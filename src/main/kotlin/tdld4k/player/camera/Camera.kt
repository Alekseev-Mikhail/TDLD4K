package tdld4k.player.camera

import tdld4k.math.RayWork
import tdld4k.player.Player
import tdld4k.player.TranslatedToRadians
import tdld4k.world.World
import java.awt.Color.BLACK
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
        drawWallsLikeColumns(g2d, translatedPlayer)
        drawWallsLikeParallelograms(g2d, translatedPlayer)
        cameraLayers.top(g2d)
        fps++

        g2d.paint = prevPaint
    }

    private fun drawWallsLikeParallelograms(g2d: Graphics2D, translatedPlayer: TranslatedToRadians) {
        val coordinatesOfWalls = mutableListOf<Pair<Point, Point>>()
        val direction = translatedPlayer.xDirection
        val fov = translatedPlayer.fov
        val firstAngle = direction - fov / 2
        val firstRc = rayWork.rayCasting(firstAngle)

        fun addPairOfPointToList(wallDistance: Double, angle: Double, x: Int) {
            val column = height / (wallDistance * cos(angle - direction))
            val rootOfColumn = height / 2 * player.yDirection
            val topColumn = column * (1 - player.y)
            val bottomColumn = column * player.y
            coordinatesOfWalls.add(
                Pair(
                    Point(
                        x,
                        (rootOfColumn - topColumn).roundToInt(),
                    ),
                    Point(
                        x,
                        (rootOfColumn + bottomColumn).roundToInt(),
                    ),
                ),
            )
        }

        var lastTileShape = Point(0, 0)
        var lastWallDistance = firstRc.wallDistance
        var lastAngle = firstAngle

        for (x in 0 until width) {
            val angle = direction - fov / 2 + fov * x / width
            val rc = rayWork.rayCasting(angle)
            val currentTileShape = Point(rc.xMap, rc.zMap)

            if (currentTileShape != lastTileShape) {
                addPairOfPointToList(lastWallDistance, lastAngle, x - 1)
                addPairOfPointToList(rc.wallDistance, angle, x)
            }
            lastTileShape = currentTileShape
            lastWallDistance = rc.wallDistance
            lastAngle = angle
        }
        addPairOfPointToList(lastWallDistance, lastAngle, width)

        val polygon = Polygon(
            IntArray(coordinatesOfWalls.size * 2),
            IntArray(coordinatesOfWalls.size * 2),
            coordinatesOfWalls.size * 2,
        )
        coordinatesOfWalls.forEachIndexed { i, e ->
            polygon.xpoints[i] = e.first.x
            polygon.ypoints[i] = e.first.y
        }
        coordinatesOfWalls.reverse()
        coordinatesOfWalls.forEachIndexed { i, e ->
            polygon.xpoints[coordinatesOfWalls.size + i] = e.second.x
            polygon.ypoints[coordinatesOfWalls.size + i] = e.second.y
        }
        g2d.paint = BLACK
        g2d.drawPolygon(polygon)
    }

    private fun drawWallsLikeColumns(g2d: Graphics2D, translatedPlayer: TranslatedToRadians) {
        val rayWork = RayWork(world, player)
        val direction = translatedPlayer.xDirection
        val fov = translatedPlayer.fov

        for (x in 0 until width) {
            val angle = direction - fov / 2 + fov * x / width
            val rc = rayWork.rayCasting(angle)
            if (rc.tileShape != null) {
                val currentColumn = height / (rc.wallDistance * cos(angle - direction))
                drawColumn(g2d, rc.tileShape.paint, x, currentColumn)
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
