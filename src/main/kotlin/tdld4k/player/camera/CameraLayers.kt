package tdld4k.player.camera

import java.awt.Graphics2D
import javax.swing.JPanel

interface CameraLayers {
    fun bottom(g2d: Graphics2D)
    fun top(g2d: Graphics2D)
    fun addComponents(panel: JPanel)
}
