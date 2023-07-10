package tdld4k.player.camera

import tdld4k.math.Ray
import tdld4k.math.RayHandle
import tdld4k.player.Player
import tdld4k.player.TranslatedToRadians
import tdld4k.player.camera.WallSide.BACK
import tdld4k.player.camera.WallSide.FORWARD
import tdld4k.player.camera.WallSide.LEFT
import tdld4k.player.camera.WallSide.NONE
import tdld4k.player.camera.WallSide.RIGHT
import tdld4k.world.Tile
import tdld4k.world.World
import java.awt.Color.BLACK
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Polygon
import java.awt.RenderingHints.KEY_ANTIALIASING
import java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt

class Camera(
    private val world: World,
    private val player: Player,
) : JPanel() {
    private val rayHandle = RayHandle(world, player)
    private var cameraLayers: CameraLayers = CameraLayersAdapter()
    private var fps = 0
    private var translatedPlayer = TranslatedToRadians(0.0, 0.0)
    private var isDebugVision = false
    val fpsCounter = Timer(1_000) {
        player.fps = fps
        fps = 0
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        val prevPaint = g2d.paint

        g2d.setRenderingHint(
            KEY_ANTIALIASING,
            VALUE_ANTIALIAS_ON,
        )
        cameraLayers.bottom(g2d)
        drawWalls(g2d)
        cameraLayers.top(g2d)
        fps++

        g2d.paint = prevPaint
    }

    private fun drawWalls(g2d: Graphics2D) {
        translatedPlayer = player.translateToRadians()
        val polygons = mutableListOf<Polygon>()
        val tiles = mutableListOf<Tile>()
        val quality = 1 / player.quality

        var lastRay = fromRayCasting(0)
        var lastSide = if (!lastRay.tile.isAir) getSide(lastRay, quality) else NONE
        if (!lastRay.tile.isAir) {
            tiles.add(lastRay.tile)
            polygons.addPolygon(lastRay.distance, lastRay.angle, -1)
        }

        for (x in 1 until width) {
            val ray = fromRayCasting(x)
            val currentSide: WallSide

            if (!ray.tile.isAir) {
                currentSide = getSide(ray, quality)
                if (lastSide != NONE) {
                    if (ray.tile != lastRay.tile || lastSide != currentSide) {
                        tiles.add(ray.tile)
                        polygons.updateAndAdd(lastRay.distance, ray.distance, lastRay.angle, ray.angle, x)
                    }
                } else {
                    tiles.add(ray.tile)
                    polygons.addPolygon(ray.distance, ray.angle, x)
                }
            } else {
                currentSide = NONE
                if (lastSide != NONE) {
                    polygons.updatePolygon(lastRay.distance, lastRay.angle, x)
                }
            }
            lastRay = ray
            lastSide = currentSide
        }

        if (lastSide != NONE || lastRay.tile.isOutOfWorldTile) {
            polygons.updatePolygon(lastRay.distance, lastRay.angle, width)
        }

        if (!isDebugVision) {
            polygons.forEachIndexed { i, e ->
                g2d.paint = tiles[i].tileShape?.paint ?: world.outOfWorldPaint
                g2d.fillPolygon(e)
            }
        } else {
            polygons.forEach { e ->
                g2d.paint = BLACK
                g2d.drawPolygon(e)
            }
        }
    }

    private fun getSide(ray: Ray, quality: Double): WallSide {
        if (!ray.tile.isOutOfWorldTile) {
            val xDistance = ray.tilePoint.x
            val yDistance = ray.tilePoint.y
            val tileShape = ray.tile.tileShape!!
            val leftTopX = tileShape.leftTop.x
            val leftTopY = tileShape.leftTop.y
            val rightBotX = tileShape.rightBot.x
            val rightBotY = tileShape.rightBot.y
            return when {
                yDistance in leftTopY..leftTopY + quality -> FORWARD
                yDistance in rightBotY - quality..rightBotY -> BACK
                xDistance in leftTopX..leftTopX + quality -> LEFT
                xDistance in rightBotX - quality..rightBotX -> RIGHT
                else -> NONE
            }
        } else {
            val xDistance = abs(ray.tilePoint.x)
            val yDistance = abs(ray.tilePoint.y)
            val tileSize = world.tileSize
            return when {
                yDistance in 0.0..quality -> FORWARD
                yDistance in tileSize - quality..tileSize -> BACK
                xDistance in 0.0..quality -> LEFT
                xDistance in tileSize - quality..tileSize -> RIGHT
                else -> NONE
            }
        }
    }

    private fun MutableList<Polygon>.updateAndAdd(
        lastWallDistance: Double,
        wallDistance: Double,
        lastAngle: Double,
        angle: Double,
        x: Int,
    ) {
        updatePolygon(lastWallDistance, lastAngle, x)
        addPolygon(wallDistance, angle, x)
    }

    private fun MutableList<Polygon>.updatePolygon(
        wallDistance: Double,
        angle: Double,
        x: Int,
    ) {
        val lastPolygon = get(size - 1)
        val columnHeight = height / (wallDistance * cos(angle - translatedPlayer.xDirection))
        val rootOfColumn = height / 2 * player.yDirection
        val topColumn = columnHeight * (1 - player.y)
        val bottomColumn = columnHeight * player.y

        lastPolygon.xpoints[1] = x
        lastPolygon.xpoints[2] = x

        lastPolygon.ypoints[1] = (rootOfColumn - topColumn).roundToInt()
        lastPolygon.ypoints[2] = (rootOfColumn + bottomColumn).roundToInt()
    }

    private fun MutableList<Polygon>.addPolygon(
        wallDistance: Double,
        angle: Double,
        x: Int,
    ) {
        val newPolygon = Polygon(IntArray(4), IntArray(4), 4)
        val columnHeight = height / (wallDistance * cos(angle - translatedPlayer.xDirection))
        val rootOfColumn = height / 2 * player.yDirection
        val topColumn = columnHeight * (1 - player.y)
        val bottomColumn = columnHeight * player.y

        newPolygon.xpoints[0] = x
        newPolygon.xpoints[3] = x

        newPolygon.ypoints[0] = (rootOfColumn - topColumn).roundToInt()
        newPolygon.ypoints[3] = (rootOfColumn + bottomColumn).roundToInt()

        add(newPolygon)
    }

    private fun fromRayCasting(x: Int) =
        rayHandle.rayCasting(translatedPlayer.xDirection - translatedPlayer.fov / 2 + translatedPlayer.fov * x / width)

    fun addComponents() {
        cameraLayers.addComponents(this)
    }

    fun setCameraLayers(cameraLayers: CameraLayers) {
        this.cameraLayers = cameraLayers
    }

    fun enableDebugVision() {
        isDebugVision = true
    }

    fun disableDebugVision() {
        isDebugVision = false
    }
}

private enum class WallSide {
    FORWARD,
    BACK,
    LEFT,
    RIGHT,
    NONE,
}
