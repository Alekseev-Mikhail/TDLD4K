package tdld4k.player.camera

import tdld4k.math.RayWork
import tdld4k.player.Player
import tdld4k.player.TranslatedToRadians
import tdld4k.player.camera.WallPart.BACK
import tdld4k.player.camera.WallPart.FORWARD
import tdld4k.player.camera.WallPart.LEFT
import tdld4k.player.camera.WallPart.NOTHING
import tdld4k.player.camera.WallPart.RIGHT
import tdld4k.world.AirTile
import tdld4k.world.Tile
import tdld4k.world.TileShape
import tdld4k.world.World
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon
import java.awt.RenderingHints
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.math.cos
import kotlin.math.roundToInt

class Camera(
    private val world: World,
    private val player: Player,
) : JPanel() {
    private val rayWork = RayWork(world, player)
    private var cameraLayers: CameraLayers = CameraLayersAdapter()
    private var fps = 0
    val fpsCounter = Timer(1_000) {
        player.fps = fps
        fps = 0
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val translatedPlayer = player.translateToRadians()
        val g2d = g as Graphics2D
        val prevPaint = g2d.paint

        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON,
        )
        cameraLayers.bottom(g2d)
        drawWallsLikeParallelograms(g2d, translatedPlayer)
        cameraLayers.top(g2d)
        fps++

        g2d.paint = prevPaint
    }

    private fun drawWallsLikeParallelograms(g2d: Graphics2D, translatedPlayer: TranslatedToRadians) {
        val polygons = mutableListOf<Polygon>()
        val tiles = mutableListOf<Tile>()
        val direction = translatedPlayer.xDirection
        val fov = translatedPlayer.fov
        val quality = 1 / player.quality
        val firstAngle = direction - fov / 2
        val firstRc = rayWork.rayCasting(firstAngle)
        val xFirstDistance = firstRc.xDistanceToStart
        val yFirstDistance = firstRc.yDistanceToStart
        val firstTile = firstRc.tile

        var coordinatesLastTile = Point(firstRc.xMap, firstRc.yMap)
        var lastWallDistance = firstRc.wallDistance
        var lastAngle = firstAngle
        var lastSide = NOTHING
        if (firstTile !is AirTile) {
            val firstTileShape = firstTile as TileShape
            val firstLeftTopX = firstTileShape.leftTop.x
            val firstLeftTopY = firstTileShape.leftTop.y
            val firstRightBotX = firstTileShape.rightBot.x
            val firstRightBotY = firstTileShape.rightBot.y
            lastSide = when {
                yFirstDistance in firstLeftTopY..firstLeftTopY + quality -> FORWARD
                yFirstDistance in firstRightBotY - quality..firstRightBotY -> BACK
                xFirstDistance in firstLeftTopX..firstLeftTopX + quality -> LEFT
                xFirstDistance in firstRightBotX - quality..firstRightBotX -> RIGHT
                else -> NOTHING
            }
        }

        tiles.add(world[coordinatesLastTile.x, coordinatesLastTile.y])
        addPolygon(polygons, direction, lastWallDistance, lastAngle, 0)
        for (x in 0 until width) {
            val angle = direction - fov / 2 + fov * x / width
            val rc = rayWork.rayCasting(angle)
            val coordinatesCurrentTile = Point(rc.xMap, rc.yMap)
            val xDistance = rc.xDistanceToStart
            val yDistance = rc.yDistanceToStart
            val currentTile = rc.tile

            if (currentTile !is AirTile) {
                val currentTileShape = currentTile as TileShape
                val leftTopX = currentTile.leftTop.x
                val leftTopY = currentTile.leftTop.y
                val rightBotX = currentTile.rightBot.x
                val rightBotY = currentTile.rightBot.y
                val currentSide = when {
                    yDistance in leftTopY..leftTopY + quality -> FORWARD
                    yDistance in rightBotY - quality..rightBotY -> BACK
                    xDistance in leftTopX..leftTopX + quality -> LEFT
                    xDistance in rightBotX - quality..rightBotX -> RIGHT
                    else -> NOTHING
                }

                if (lastSide == NOTHING) {
                    addPolygon(polygons, direction, rc.wallDistance, angle, x)
                    tiles.add(currentTileShape)
                } else if (coordinatesCurrentTile != coordinatesLastTile || currentSide != lastSide) {
                    tiles.add(currentTileShape)
                    addPolygonAndUpdateLast(polygons, direction, lastWallDistance, rc.wallDistance, lastAngle, angle, x)
                }

                lastSide = currentSide
            } else if (lastSide != NOTHING) {
                lastSide = NOTHING
                updatePolygon(polygons, direction, lastWallDistance, lastAngle, x - 1)
            }
            coordinatesLastTile = coordinatesCurrentTile
            lastWallDistance = rc.wallDistance
            lastAngle = angle
        }
        if (lastSide != NOTHING) {
            updatePolygon(polygons, direction, lastWallDistance, lastAngle, width)
        }

        polygons.forEachIndexed { i, e ->
            g2d.paint = (tiles[i] as TileShape).paint
            g2d.fillPolygon(e)
        }
    }

    private fun addPolygonAndUpdateLast(
        polygons: MutableList<Polygon>,
        direction: Double,
        lastWallDistance: Double,
        wallDistance: Double,
        lastAngle: Double,
        angle: Double,
        x: Int,
    ) {
        updatePolygon(polygons, direction, lastWallDistance, lastAngle, x)
        addPolygon(polygons, direction, wallDistance, angle, x)
    }

    private fun updatePolygon(
        polygons: MutableList<Polygon>,
        direction: Double,
        wallDistance: Double,
        angle: Double,
        x: Int,
    ) {
        val lastPolygon = polygons[polygons.size - 1]
        val column = height / (wallDistance * cos(angle - direction))
        val rootOfColumn = height / 2 * player.yDirection
        val topColumn = column * (1 - player.y)
        val bottomColumn = column * player.y

        lastPolygon.xpoints[1] = x
        lastPolygon.xpoints[2] = x

        lastPolygon.ypoints[1] = (rootOfColumn - topColumn).roundToInt()
        lastPolygon.ypoints[2] = (rootOfColumn + bottomColumn).roundToInt()
    }

    private fun addPolygon(
        polygons: MutableList<Polygon>,
        direction: Double,
        wallDistance: Double,
        angle: Double,
        x: Int,
    ) {
        val column = height / (wallDistance * cos(angle - direction))
        val rootOfColumn = height / 2 * player.yDirection
        val topColumn = column * (1 - player.y)
        val bottomColumn = column * player.y
        val xFirstTwoPoint = IntArray(4)
        val yFirstTwoPoint = IntArray(4)

        xFirstTwoPoint[0] = x
        xFirstTwoPoint[3] = x

        yFirstTwoPoint[0] = (rootOfColumn - topColumn).roundToInt()
        yFirstTwoPoint[3] = (rootOfColumn + bottomColumn).roundToInt()

        polygons.add(Polygon(xFirstTwoPoint, yFirstTwoPoint, 4))
    }

    fun addComponents() {
        cameraLayers.addComponents(this)
    }

    fun setCameraLayers(cameraLayers: CameraLayers) {
        this.cameraLayers = cameraLayers
    }
}

private enum class WallPart {
    FORWARD,
    BACK,
    LEFT,
    RIGHT,
    NOTHING,
}
