package tdld4k.player

import tdld4k.math.GameRayCastingOutput
import tdld4k.math.GameRayWork
import tdld4k.math.Parallelogram
import tdld4k.math.Vector2Int
import tdld4k.world.GameShape
import tdld4k.world.GameWorld
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Paint
import javax.swing.JPanel
import kotlin.math.cos
import kotlin.math.roundToInt

class GameCamera(private val world: GameWorld, private val player: GamePlayer) : JPanel() {
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val translatedGamePlayer = player.translateToRadians()
        val g2d = g as Graphics2D
        val prevPaint = g2d.paint

        drawWallsLikeColumns(g2d, translatedGamePlayer)

        g2d.paint = prevPaint
    }

    private fun drawWallsLikeParallelogram(g2d: Graphics2D, translatedGamePlayer: GameTranslatedToRadians) {
        val parallelograms = mutableListOf<Parallelogram>()
        val rayWork = GameRayWork(world, player)
        val direction = translatedGamePlayer.direction
        val fov = translatedGamePlayer.fov

        var currentTile: GameShape? = null
        var lastRc: GameRayCastingOutput? = null
        var lastAngle: Double? = null
        var currentLeftTop: Vector2Int? = null
        var currentLeftBot: Vector2Int? = null
        for (x in 0 until width) {
            val angle = direction - fov / 2 + fov * x / width
            val rc = rayWork.rayCasting(angle)

            val shape = rc.shape ?: continue
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

    private fun drawWallsLikeColumns(g2d: Graphics2D, translatedGamePlayer: GameTranslatedToRadians) {
        val rayWork = GameRayWork(world, player)
        val direction = translatedGamePlayer.direction
        val fov = translatedGamePlayer.fov

        for (x in 0 until width) {
            val angle = direction - fov / 2 + fov * x / width
            val rc = rayWork.rayCasting(angle)
            if (rc.shape != null) {
                val currentColumn = height / (rc.wallDistance * cos(angle - direction))
                drawColumn(g2d, rc.shape.paint, x, currentColumn)
            }
        }
    }

    private fun drawColumn(g2d: Graphics2D, paint: Paint, x: Int, currentColumn: Double) {
        g2d.paint = paint
        g2d.drawLine(
            x,
            (height / 2 - currentColumn / 2).roundToInt(),
            x,
            (height / 2 + currentColumn / 2).roundToInt(),
        )
    }
}
