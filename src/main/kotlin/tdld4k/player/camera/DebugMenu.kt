package tdld4k.player.camera

import java.awt.Font
import java.awt.Graphics2D
import java.awt.Paint
import java.awt.Point

abstract class DebugMenu(
    private val point: Point,
    private val fontValue: Int,
    private val margin: Int,
    private val labelPaint: Paint,
    private val textPaint: Paint,
) : CameraLayersAdapter() {
    private val debugObjects = mutableListOf<DebugObject>()

    override fun top(g2d: Graphics2D) {
        val font = Font("debug menu", fontValue, 20)
        val xLabel = point.x
        var yLabel = point.y

        debugObjects.forEach { o ->
            o.debugItems.forEach { i ->
                val text = "${i.key}: ${i.value}"
                val heightLabel = font.size + margin * 2
                val widthLabel = text.length * font.size + margin * 2

                g2d.paint = labelPaint
                g2d.fillRect(xLabel, yLabel, widthLabel, heightLabel)
                g2d.paint = textPaint
                g2d.font = font
                g2d.drawString(text, xLabel + margin, yLabel + margin + font.size)
                yLabel += heightLabel
            }
        }
    }

    fun addDebugObject(debugObject: DebugObject) {
        debugObjects.add(debugObject)
    }
}