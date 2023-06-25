package example

import tdld4k.SingleClient
import tdld4k.debug.DebugMenu
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics2D
import java.awt.Paint
import java.awt.Point
import java.awt.Toolkit
import javax.swing.JPanel
import javax.swing.JSlider

class Menus(
    private val singleClient: SingleClient,
    private val player: ExamplePlayer,
    private val labelPaint: Paint,
    private val margin: Int,
    private val font: Font,
    private val textPaint: Paint,
    point: Point,
) : DebugMenu(
    font,
    point,
    margin,
    labelPaint,
    textPaint,
) {
    val sliders = List(2) { JSlider() }
    private val descriptions = mutableListOf(
        "FOV: ${player.fov}",
        "Render Distance: ${player.renderDistance}",
    )

    override fun top(g2d: Graphics2D) {
        Toolkit.getDefaultToolkit().screenSize
        if (player.isEscape) {
            g2d.paint = labelPaint
            g2d.fillRect(0, 0, singleClient.playerFrame.width, singleClient.playerFrame.height)

            descriptions[0] = "FOV: ${player.fov}"
            descriptions[1] = "Render Distance: ${player.renderDistance}"

            var ySlider = 0
            g2d.paint = textPaint
            g2d.font = font
            sliders.forEachIndexed { i, e ->
                val nextYSliderWithoutMargins = ySlider + e.height + font.size
                e.isFocusable = false
                e.size = Dimension(singleClient.playerFrame.width / 3, e.height)
                e.location = Point(0, ySlider)
                g2d.drawString(descriptions[i], margin, nextYSliderWithoutMargins + margin)
                ySlider += nextYSliderWithoutMargins + margin * 2
            }

            player.fov = sliders[0].value.toDouble()
            player.renderDistance = sliders[1].value.toDouble()
        } else if (player.isShowDebugMenu) {
            super.top(g2d)
        }
    }

    override fun addComponents(panel: JPanel) {
        sliders.forEach { e ->
            e.isVisible = false
            e.maximum = 360
            e.value = player.fov.toInt()
            panel.add(e)
        }
    }
}
