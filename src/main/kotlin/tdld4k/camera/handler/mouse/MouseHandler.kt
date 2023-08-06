package tdld4k.camera.handler.mouse

import tdld4k.camera.handler.Handler
import tdld4k.camera.window.Window

abstract class MouseHandler : Handler {
    private var lastX = 0.0
    private var lastY = 0.0

    override fun setCallback(window: Window) {
        window.setCursorPosCallback { x, y ->
            motion(MouseMotion(window, lastX, lastY, x, y))
            lastX = x
            lastY = y
        }
    }

    protected open fun motion(motion: MouseMotion) {}
}
