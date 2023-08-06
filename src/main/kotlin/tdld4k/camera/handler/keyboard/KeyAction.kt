package tdld4k.camera.handler.keyboard

import tdld4k.camera.window.Window

data class KeyAction(
    val window: Window,
    val key: Int,
    val scancode: Int,
    val mods: Int,
)
