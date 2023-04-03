package TDLD4k

import javax.swing.JPanel

class TDLD4K(
    frameTitle: String,
    frameWidth: Int,
    frameHeight: Int,
    resizable: Boolean = true,
    visible: Boolean = false,
    panel: JPanel? = null,
    controller: Controller? = null
) {
    val camera = Camera(frameTitle, frameWidth, frameHeight, resizable, visible, panel, controller)
}