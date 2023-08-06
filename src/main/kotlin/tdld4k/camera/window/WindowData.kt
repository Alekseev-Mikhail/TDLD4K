package tdld4k.camera.window

import org.lwjgl.system.MemoryUtil

fun emptyWindowData() = WindowData(MemoryUtil.NULL, 0, 0, 0, 0, 0)

data class WindowData(
    var id: Long,
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
    var fps: Int,
)
