package tdld4k.player.camera

import java.awt.Graphics2D

abstract class CameraLayersAdapter : CameraLayersInterface {
    override fun bottom(g2d: Graphics2D) {}
    override fun top(g2d: Graphics2D) {}
}