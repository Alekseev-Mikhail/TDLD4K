package tdld4k.debug

import tdld4k.player.camera.CameraLayersAdapter
import java.awt.Font
import java.awt.Graphics2D
import java.awt.Paint
import java.awt.Point
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform

abstract class DebugMenu(
    fontSize: Int,
    fontValue: Int,
    private val point: Point,
    private val margin: Int,
    private val labelPaint: Paint,
    private val textPaint: Paint,
) : CameraLayersAdapter() {
    private val debugObjects = mutableListOf<DebugObject?>()
    private val font = Font("debug menu", fontValue, fontSize)
    private val xLabel = point.x
    private var yLabel = point.y
    private val heightLabel = font.size + margin * 2

    fun addDebugObject(debugObject: DebugObject?) {
        debugObjects.add(debugObject)
    }

    override fun top(g2d: Graphics2D) {
        debugObjects.forEach { o ->
            o?.debugItems?.forEach { i ->
                if (i.key == "Pass" && i.value == null) {
                    addPass()
                } else {
                    val text = "${i.key}: ${i.value}"
                    val affineTransform = AffineTransform()
                    val fontRenderContext = FontRenderContext(affineTransform, true, true)
                    val widthLabel = font.getStringBounds(text, fontRenderContext).width + margin * 2

                    g2d.paint = labelPaint
                    g2d.fillRect(xLabel, yLabel, widthLabel.toInt(), heightLabel)
                    g2d.paint = textPaint
                    g2d.font = font
                    g2d.drawString(text, xLabel + margin, yLabel + margin + font.size)
                    yLabel += heightLabel
                }
            } ?: addPass()
        }
        yLabel = point.y
    }

    private fun addPass() {
        yLabel += heightLabel
    }
}
