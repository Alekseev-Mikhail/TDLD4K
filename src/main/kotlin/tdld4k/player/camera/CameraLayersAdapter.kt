package tdld4k.player.camera

import java.awt.Graphics2D
import javax.swing.JPanel

open class CameraLayersAdapter : CameraLayers {
    override fun bottom(g2d: Graphics2D) {}
    override fun top(g2d: Graphics2D) {}
    override fun addComponents(panel: JPanel) {}
}
