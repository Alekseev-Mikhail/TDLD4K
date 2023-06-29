package tdld4k.player.camera

import tdld4k.math.RayWork
import tdld4k.player.Player
import tdld4k.player.TranslatedToRadians
import tdld4k.player.camera.WallPart.BOT
import tdld4k.player.camera.WallPart.LEFT
import tdld4k.player.camera.WallPart.NOTHING
import tdld4k.player.camera.WallPart.RIGHT
import tdld4k.player.camera.WallPart.TOP
import tdld4k.world.AirTile
import tdld4k.world.Tile
import tdld4k.world.TileShape
import tdld4k.world.World
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Paint
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
        val firstAngle = direction - fov / 2
        val firstRc = rayWork.rayCasting(firstAngle)
        val xFirstDistance = firstRc.xDistanceToStart
        val yFirstDistance = firstRc.yDistanceToStart
        val firstTileShape = firstRc.tile as TileShape

        var coordinatesLastTile = Point(firstRc.xMap, firstRc.yMap)
        var lastWallDistance = firstRc.wallDistance
        var lastAngle = firstAngle
        var lastSide = when {
            xFirstDistance > yFirstDistance && yFirstDistance < player.quality -> TOP
            xFirstDistance < yFirstDistance && yFirstDistance > firstTileShape.rightBot.y - player.quality -> BOT
            xFirstDistance < yFirstDistance && xFirstDistance < player.quality -> LEFT
            xFirstDistance > yFirstDistance && xFirstDistance > firstTileShape.rightBot.x - player.quality -> RIGHT
            else -> NOTHING
        }

        tiles.add(world[coordinatesLastTile.x, coordinatesLastTile.y])
        addPolygon(polygons, direction, lastWallDistance, lastAngle, 0)
        for (x in 0 until width) {
            val angle = direction - fov / 2 + fov * x / width
            val rc = rayWork.rayCasting(angle)
            val coordinatesCurrentTile = Point(rc.xMap, rc.yMap)
            val xDistance = rc.xDistanceToStart
            val yDistance = rc.yDistanceToStart
            val currentTileShape = rc.tile as TileShape
            val currentSide = when {
                xDistance > yDistance && yDistance < currentTileShape.leftTop.y + player.quality -> TOP
                xDistance < yDistance && yDistance > currentTileShape.rightBot.y - player.quality -> BOT
                xDistance < yDistance && xDistance < currentTileShape.leftTop.x + player.quality -> LEFT
                xDistance > yDistance && xDistance > currentTileShape.rightBot.x - player.quality -> RIGHT
                else -> NOTHING
            }

            if (coordinatesCurrentTile != coordinatesLastTile || currentSide != lastSide) {
                tiles.add(world[coordinatesCurrentTile.x, coordinatesCurrentTile.y])
                addPolygonAndUpdateLast(polygons, direction, lastWallDistance, rc.wallDistance, lastAngle, angle, x)
            }
            coordinatesLastTile = coordinatesCurrentTile
            lastWallDistance = rc.wallDistance
            lastAngle = angle
            lastSide = currentSide
        }
        updatePolygon(polygons, direction, lastWallDistance, lastAngle, width)

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

    private fun drawWallsLikeColumns(g2d: Graphics2D, translatedPlayer: TranslatedToRadians) {
        val rayWork = RayWork(world, player)
        val direction = translatedPlayer.xDirection
        val fov = translatedPlayer.fov

        for (x in 0 until width) {
            val angle = direction - fov / 2 + fov * x / width
            val rc = rayWork.rayCasting(angle)
            if (rc.tile != AirTile()) {
                val currentColumn = height / (rc.wallDistance * cos(angle - direction))
                rc.tile as TileShape
                drawColumn(g2d, rc.tile.paint, x, currentColumn)
            }
        }
    }

    private fun drawColumn(g2d: Graphics2D, paint: Paint, x: Int, currentColumn: Double) {
        val rootOfColumn = height / 2 * player.yDirection
        val topColumn = currentColumn * (1 - player.y)
        val bottomColumn = currentColumn * player.y
        g2d.paint = paint
        g2d.drawLine(
            x,
            (rootOfColumn - topColumn).roundToInt(),
            x,
            (rootOfColumn + bottomColumn).roundToInt(),
        )
    }

    fun addComponents() {
        cameraLayers.addComponents(this)
    }

    fun setCameraLayers(cameraLayers: CameraLayers) {
        this.cameraLayers = cameraLayers
    }
}

private enum class WallPart {
    TOP,
    BOT,
    LEFT,
    RIGHT,
    NOTHING,
}
