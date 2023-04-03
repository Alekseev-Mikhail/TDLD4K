package TDLD4k

import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel

class Camera(
    private val title: String,
    private val frameWidth: Int,
    private val frameHeight: Int,
    private val resizable: Boolean,
    private val visible: Boolean,
    private val panel: JPanel? = null,
    private val controller: Controller? = null
) {
    var cameraFrame: JFrame? = null

    fun createFrame() {
        val frame = JFrame(title)
        if (panel != null) {
            frame.contentPane = panel.apply {
                preferredSize = Dimension(frameWidth, frameHeight)
            }
        }
        frame.addKeyListener(controller)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.pack()
        frame.isResizable = resizable
        frame.isVisible = visible
        cameraFrame = frame
    }
}