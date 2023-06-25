package tdld4k.player.camera

import tdld4k.math.GameRayWork
import tdld4k.math.Parallelogram
import tdld4k.math.RayCastingOutput
import tdld4k.math.Vector2Int
import tdld4k.player.Player
import tdld4k.player.TranslatedToRadians
import tdld4k.world.TileShape
import tdld4k.world.World
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Paint
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.math.cos
import kotlin.math.roundToInt

class Camera(
    private val world: World,
    private val player: Player,
) : JPanel() {
    private var cameraLayers: CameraLayers = CameraLayersAdapter()
    private var fps = 0
    val fpsCounter = Timer(1_000) {
        player.fps = fps
        fps = 0
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val translatedGamePlayer = player.translateToRadians()
        val g2d = g as Graphics2D
        val prevPaint = g2d.paint

        cameraLayers.bottom(g2d)
        drawWallsLikeColumns(g2d, translatedGamePlayer)
        cameraLayers.top(g2d)
        fps++

        g2d.paint = prevPaint
    }

    private fun drawWallsLikeParallelogram(g2d: Graphics2D, translatedGamePlayer: TranslatedToRadians) {
        val parallelograms = mutableListOf<Parallelogram>()
        val rayWork = GameRayWork(world, player)
        val direction = translatedGamePlayer.direction
        val fov = translatedGamePlayer.fov

        var currentTile: TileShape? = null
        var lastRc: RayCastingOutput? = null
        var lastAngle: Double? = null
        var currentLeftTop: Vector2Int? = null
        var currentLeftBot: Vector2Int? = null
        for (x in 0 until width) {
            val angle = direction - fov / 2 + fov * x / width
            val rc = rayWork.rayCasting(angle)

            val shape = rc.tileShape ?: continue
            if (currentTile == null) {
                val currentColumn = height / (rc.wallDistance * cos(angle - direction))
                currentTile = shape
                lastRc = rc
                lastAngle = angle
                currentLeftTop = Vector2Int(x, (height / 2 - currentColumn / 2).roundToInt())
                currentLeftBot = Vector2Int(x, (height / 2 + currentColumn / 2).roundToInt())
                continue
            }
            if (shape == currentTile) {
                lastRc = rc
                lastAngle = angle
                continue
            }
            val lastColumn = height / (lastRc!!.wallDistance * cos(lastAngle!! - direction))
            currentTile = shape
            parallelograms.add(
                Parallelogram(
                    currentLeftTop!!,
                    currentLeftBot!!,
                    Vector2Int(x, (height / 2 - lastColumn / 2).roundToInt()),
                    Vector2Int(x, (height / 2 + lastColumn / 2).roundToInt()),
                ),
            )
        }

        for (i in 0 until parallelograms.size) {
            val leftTop = parallelograms[i].leftTop
            val leftBot = parallelograms[i].leftBot
            val rightTop = parallelograms[i].rightTop
            val rightBot = parallelograms[i].rightBot
            g2d.fillPolygon(
                intArrayOf(leftTop.x, leftBot.x, rightBot.x, rightTop.x),
                intArrayOf(leftTop.y, leftBot.y, rightBot.y, rightTop.y),
                4,
            )
        }
    }

    private fun drawWallsLikeColumns(g2d: Graphics2D, translatedGamePlayer: TranslatedToRadians) {
        val rayWork = GameRayWork(world, player)
        val direction = translatedGamePlayer.direction
        val fov = translatedGamePlayer.fov

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
        val halfHeight = height / 2 * player.directionY
        val topColumn = currentColumn * (1 - player.y)
        val bottomColumn = currentColumn * player.y
        g2d.paint = paint
        g2d.drawLine(
            x,
            (halfHeight - topColumn).roundToInt(),
            x,
            (halfHeight + bottomColumn).roundToInt(),
        )
    }

    fun addComponents() {
        cameraLayers.addComponents(this)
    }

    fun setCameraLayers(cameraLayers: CameraLayers) {
        this.cameraLayers = cameraLayers
    }
}
