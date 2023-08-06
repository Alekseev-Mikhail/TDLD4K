package tdld4k.camera.handler.mouse

import tdld4k.camera.window.Window

data class MouseMotion(
    val window: Window,
    val lastX: Double,
    val lastY: Double,
    val x: Double,
    val y: Double,
)
