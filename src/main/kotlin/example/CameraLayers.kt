package example

import tdld4k.player.camera.DebugMenu
import java.awt.Graphics2D
import java.awt.Paint
import java.awt.Point

class CameraLayers(
    private val player: ExamplePlayer,
    point: Point,
    fontValue: Int,
    margin: Int,
    labelPaint: Paint,
    textPaint: Paint
) : DebugMenu(
    point,
    fontValue,
    margin,
    labelPaint,
    textPaint
) {
    override fun top(g2d: Graphics2D) {
        if (player.isShowDebugMenu) {
            super.top(g2d)
        }
    }
}