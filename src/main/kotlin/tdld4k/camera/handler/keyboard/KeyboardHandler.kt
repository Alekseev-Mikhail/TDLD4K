package tdld4k.camera.handler.keyboard

import tdld4k.camera.handler.Handler
import tdld4k.camera.handler.Key.KEY_PRESS
import tdld4k.camera.handler.Key.KEY_RELEASE
import tdld4k.camera.handler.Key.KEY_REPEAT
import tdld4k.camera.window.Window

abstract class KeyboardHandler : Handler {
    override fun setCallback(window: Window) {
        window.setKeyCallback { key, scancode, actionType, mods ->
            keyAction(KeyAction(window, key, scancode, mods), actionType)
        }
    }

    private fun keyAction(action: KeyAction, actionType: Int) {
        when (actionType) {
            KEY_RELEASE.value -> keyRelease(action)
            KEY_PRESS.value -> keyPress(action)
            KEY_REPEAT.value -> keyRepeat(action)
        }
    }

    protected open fun keyPress(action: KeyAction) {}

    protected open fun keyRelease(action: KeyAction) {}

    protected open fun keyRepeat(action: KeyAction) {}
}
