package example

import tdld4k.debug.DebugMenu
import java.awt.Font
import java.awt.Paint
import java.awt.Point
import javax.swing.JButton
import javax.swing.JSlider
import kotlin.system.exitProcess

class Menus(
//    private val client: Client,
    private val player: Player,
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
    val exitButton = JButton()
    val sliders = mutableListOf<JSlider>()

//    override fun top(g2d: Graphics2D) {
//        if (player.isEscape) {
//            g2d.paint = labelPaint
//            g2d.fillRect(0, 0, client.playerFrame.width, client.playerFrame.height)
//
//            g2d.paint = textPaint
//            g2d.font = font
//
//            var ySlider = 0
//            sliders.forEachIndexed { i, slider ->
//                val yText = ySlider + slider.height + font.size + margin
//                slider.size = Dimension(client.playerFrame.width / 3, slider.height)
//                slider.location = Point(0, ySlider)
//                g2d.drawString(descriptions[i], margin, yText)
//                ySlider = yText + margin
//            }
//
//            exitButton.location = Point(client.playerFrame.width - exitButton.width, 0)
//        } else if (player.isShowDebugMenu) {
//            super.top(g2d)
//        }
//    }

//    override fun addComponents(panel: JPanel) {
//        for (i in 0..2) {
//            val slider = JSlider()
//            if (!player.isEscape) {
//                slider.isVisible = false
//            }
//            slider.isFocusable = false
//            when (i) {
//                0 -> {
//                    slider.maximum = 90
//                    slider.minimum = 60
//                    slider.value = player.fov.roundToInt()
//                    val value = slider.value
//                    player.fov = value.toDouble()
//                    descriptions.add("FOV: $value")
//                    slider.addChangeListener { changeFov(slider.value) }
//                }
//
//                1 -> {
//                    slider.maximum = 50
//                    slider.value = player.renderDistance.roundToInt()
//                    val value = slider.value
//                    player.renderDistance = value.toDouble()
//                    descriptions.add("Render Distance: $value")
//                    slider.addChangeListener { changeRenderDistance(slider.value) }
//                }
//
//                2 -> {
//                    slider.maximum = 200
//                    slider.minimum = 1
//                    slider.value = player.quality.roundToInt()
//                    val value = slider.value
//                    player.quality = value.toDouble()
//                    descriptions.add("Quality: $value")
//                    slider.addChangeListener { changeQuality(slider.value) }
//                }
//            }
//            sliders.add(slider)
//            panel.add(slider)
//        }
//
//        if (!player.isEscape) {
//            exitButton.isVisible = false
//        }
//        exitButton.text = "Exit"
//        exitButton.addChangeListener { exitFomGame() }
//        panel.add(exitButton)
//    }

//    private fun changeFov(value: Int) {
//        player.fov = value.toDouble()
//        descriptions[0] = "FOV: $value"
//    }
//
//    private fun changeRenderDistance(value: Int) {
//        player.renderDistance = value.toDouble()
//        descriptions[1] = "Render Distance: $value"
//    }
//
//    private fun changeQuality(value: Int) {
//        player.quality = value.toDouble()
//        descriptions[2] = "Quality: $value"
//    }

    private fun exitFomGame() {
        exitProcess(0)
    }
}
