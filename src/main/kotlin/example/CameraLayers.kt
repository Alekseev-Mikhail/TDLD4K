package example

import tdld4k.player.camera.CameraLayersAdapter
import java.awt.Graphics2D

class CameraLayers: CameraLayersAdapter() {
    override fun top(g2d: Graphics2D) {
        g2d.fillRect(0,0, 10, 10)
    }
}