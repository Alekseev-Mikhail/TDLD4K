package tdld4k.camera

import org.slf4j.LoggerFactory
import tdld4k.camera.window.Window
import tdld4k.camera.window.emptyWindowData
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

class Camera {
    private val windowData = emptyWindowData()
    private val window = Window(windowData)
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val fpsCounter = Timer()
    private var numberOfFrames = 0

    fun create(
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        title: String,
        monitor: Long,
        share: Long,
        setup: (window: Window) -> Unit,
    ) {
        window.setErrorCallback { error, description -> errorCallback(error, description) }

        if (!window.create(x, y, width, height, title, monitor, share)) {
            cleanup(false)
        }

        window.setDefaultHints()
        window.content.create()

        setup(window)
        logger.info("Initial window setup completed")

        startRenderLoop()
    }

    private fun startRenderLoop() {
        fpsCounter.schedule(0, 1_000) {
            windowData.fps = numberOfFrames
            numberOfFrames = 0
        }

        logger.info("Render loop started")

        while (!window.shouldClose) {
            window.content.render(window)
            window.postRender()

            numberOfFrames++
        }

        logger.info("Render loop stopped")

        cleanup(true)
    }

    private fun cleanup(destroyWindow: Boolean) {
        window.cleanup(destroyWindow)
        logger.info("Application stopped")
        exitProcess(0)
    }

    private fun errorCallback(error: Int, description: String) {
        logger.error("Error: $error. $description")
    }
}
