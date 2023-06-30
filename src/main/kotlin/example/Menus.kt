package example

import tdld4k.SingleClient
import tdld4k.debug.DebugMenu
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics2D
import java.awt.Paint
import java.awt.Point
import javax.swing.JPanel
import javax.swing.JSlider
import kotlin.math.roundToInt

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
    private val sliderCounts = 3
    private val descriptions = MutableList(sliderCounts) { "" }
    val sliders = List(sliderCounts) { JSlider() }

    override fun top(g2d: Graphics2D) {
        if (player.isEscape) {
            g2d.paint = labelPaint
            g2d.fillRect(0, 0, singleClient.playerFrame.width, singleClient.playerFrame.height)

            g2d.paint = textPaint
            g2d.font = font

            var ySlider = 0
            sliders.forEachIndexed { i, e ->
                val yText = ySlider + e.height + font.size + margin
                e.isFocusable = false
                e.size = Dimension(singleClient.playerFrame.width / 3, e.height)
                e.location = Point(0, ySlider)
                g2d.drawString(descriptions[i], margin, yText)
                ySlider = yText + margin
            }
        } else if (player.isShowDebugMenu) {
            super.top(g2d)
        }
    }

    override fun addComponents(panel: JPanel) {
        sliders.forEachIndexed { i, slider ->
            if (!player.isEscape) {
                slider.isVisible = false
            }
            when (i) {
                0 -> {
                    slider.maximum = 90
                    slider.minimum = 60
                    slider.value = player.fov.roundToInt()
                    val value = slider.value
                    changeFov(value)
                    slider.addChangeListener { changeFov(slider.value) }
                }

                1 -> {
                    slider.maximum = 100
                    slider.value = player.renderDistance.roundToInt()
                    val value = slider.value
                    changeRenderDistance(value)
                    slider.addChangeListener { changeRenderDistance(slider.value) }
                }

                2 -> {
                    slider.maximum = 100
                    slider.minimum = 1
                    slider.value = player.quality.roundToInt()
                    val value = slider.value
                    changeQuality(value)
                    slider.addChangeListener { changeQuality(slider.value) }
                }
            }
            panel.add(slider)
        }
    }

    private fun changeFov(value: Int) {
        player.fov = value.toDouble()
        descriptions[0] = "FOV: $value"
    }

    private fun changeRenderDistance(value: Int) {
        player.renderDistance = value.toDouble()
        descriptions[1] = "Render Distance: $value"
    }

    private fun changeQuality(value: Int) {
        player.quality = value.toDouble()
        descriptions[2] = "Quality: $value"
    }
}
