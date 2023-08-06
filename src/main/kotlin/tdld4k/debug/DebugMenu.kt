package tdld4k.debug

import java.awt.Font
import java.awt.Paint
import java.awt.Point

abstract class DebugMenu(
    private val font: Font,
    private val point: Point,
    private val margin: Int,
    private val labelPaint: Paint,
    private val textPaint: Paint,
) {
    private val debugObjects = mutableListOf<DebugObjectInterface?>()
    private val xLabel = point.x
    private var yLabel = point.y
    private val heightLabel = font.size + margin * 2

    fun addDebugObject(debugObject: DebugObjectInterface?) {
        debugObjects.add(debugObject)
    }

//    override fun top(g2d: Graphics2D) {
//        debugObjects.forEach { e ->
//            e?.updateDebugItems()
//        }
//        debugObjects.forEach { o ->
//            o?.debugItems?.forEach { i ->
//                if (i.key == "Pass" && i.value == null) {
//                    addPass()
//                } else {
//                    val text = "${i.key}: ${i.value}"
//                    val affineTransform = AffineTransform()
//                    val fontRenderContext = FontRenderContext(affineTransform, true, true)
//                    val widthLabel = font.getStringBounds(text, fontRenderContext).width + margin * 2
//
//                    g2d.paint = labelPaint
//                    g2d.fillRect(xLabel, yLabel, widthLabel.toInt(), heightLabel)
//                    g2d.paint = textPaint
//                    g2d.font = font
//                    g2d.drawString(text, xLabel + margin, yLabel + margin + font.size)
//                    yLabel += heightLabel
//                }
//            } ?: addPass()
//        }
//        yLabel = point.y
//    }

    private fun addPass() {
        yLabel += heightLabel
    }
}
