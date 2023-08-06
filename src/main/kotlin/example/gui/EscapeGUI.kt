package example.gui

import tdld4k.camera.window.Window
import tdld4k.camera.window.content.Content
import tdld4k.util.BLACK

class EscapeGUI : Content() {
    override fun changeOn(window: Window) {
        super.changeOn(window)
        window.setNormalCursor()
        window.setCursorPosition(window.width / 2, window.height / 2)
    }

    override fun render(window: Window) {
        super.render(window)
        paint = BLACK
        drawRectangle(40.0, 40.0, 40.0, 40.0)
        drawRectangle(120.0, 40.0, 40.0, 40.0)
        drawRectangle(40.0, 120.0, 160.0, 40.0)
    }
}
