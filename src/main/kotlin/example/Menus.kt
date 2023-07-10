package example

import tdld4k.Client
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
    private val client: Client,
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
    private val descriptions = mutableListOf<String>()
    val sliders = mutableListOf<JSlider>()

    override fun top(g2d: Graphics2D) {
        if (player.isEscape) {
            g2d.paint = labelPaint
            g2d.fillRect(0, 0, client.playerFrame.width, client.playerFrame.height)

            g2d.paint = textPaint
            g2d.font = font

            var ySlider = 0
            sliders.forEachIndexed { i, e ->
                val yText = ySlider + e.height + font.size + margin
                e.isFocusable = false
                e.size = Dimension(client.playerFrame.width / 3, e.height)
                e.location = Point(0, ySlider)
                g2d.drawString(descriptions[i], margin, yText)
                ySlider = yText + margin
            }
        } else if (player.isShowDebugMenu) {
            super.top(g2d)
        }
    }

    override fun addComponents(panel: JPanel) {
        for (i in 0..2) {
            val slider = JSlider()
            if (!player.isEscape) {
                slider.isVisible = false
            }
            when (i) {
                0 -> {
                    slider.maximum = 90
                    slider.minimum = 60
                    slider.value = player.fov.roundToInt()
                    val value = slider.value
                    player.fov = value.toDouble()
                    descriptions.add("FOV: $value")
                    slider.addChangeListener { changeFov(slider.value) }
                }

                1 -> {
                    slider.maximum = 50
                    slider.value = player.renderDistance.roundToInt()
                    val value = slider.value
                    player.renderDistance = value.toDouble()
                    descriptions.add("Render Distance: $value")
                    slider.addChangeListener { changeRenderDistance(slider.value) }
                }

                2 -> {
                    slider.maximum = 200
                    slider.minimum = 1
                    slider.value = player.quality.roundToInt()
                    val value = slider.value
                    player.quality = value.toDouble()
                    descriptions.add("Quality: $value")
                    slider.addChangeListener { changeQuality(slider.value) }
                }
            }
            sliders.add(slider)
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
