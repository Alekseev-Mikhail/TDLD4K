package tdld4k

import org.lwjgl.system.MemoryUtil.NULL
import tdld4k.camera.Camera
import tdld4k.camera.window.Window

class Client {
    val version: String? = this::class.java.`package`.implementationVersion
    private val camera = Camera()

    fun createCamera(
        x: Int = 0,
        y: Int = 0,
        width: Int = 500,
        height: Int = 500,
        title: String = "TDLD4K",
        monitor: Long = NULL,
        share: Long = NULL,
        setup: (window: Window) -> Unit = {},
    ) {
        camera.create(x, y, width, height, title, monitor, share, setup)
    }
}
