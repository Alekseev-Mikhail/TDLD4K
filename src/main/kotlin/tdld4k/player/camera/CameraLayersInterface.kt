package tdld4k.player.camera

import java.awt.Graphics2D

interface CameraLayersInterface {
    fun bottom(g2d: Graphics2D)
    fun top(g2d: Graphics2D)
}
