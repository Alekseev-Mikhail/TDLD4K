package example

import tdld4k.debug.DebugMenu
import java.awt.Graphics2D
import java.awt.Paint
import java.awt.Point

class CameraLayers(
    private val player: ExamplePlayer,
    fontSize: Int,
    fontValue: Int,
    point: Point,
    margin: Int,
    labelPaint: Paint,
    textPaint: Paint,
) : DebugMenu(
    fontSize,
    fontValue,
    point,
    margin,
    labelPaint,
    textPaint,
) {
    override fun top(g2d: Graphics2D) {
        if (player.isShowDebugMenu) {
            super.top(g2d)
        }
    }
}
