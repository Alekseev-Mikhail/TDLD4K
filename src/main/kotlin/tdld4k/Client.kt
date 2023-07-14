package tdld4k

import org.slf4j.LoggerFactory
import tdld4k.player.Player
import tdld4k.player.camera.Camera
import tdld4k.world.World
import java.awt.Image
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.ImageIcon

class Client(
    world: World,
    player: Player,
    clientLife: ClientLife,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    val version: String? = this::class.java.`package`.implementationVersion
    val camera = Camera(world, player, clientLife, logger)

    companion object {
        fun getShader(name: String): String =
            Files.readAllBytes(Paths.get("src/main/resources/shaders/$name")).toString()

        fun getImage(name: String): Image = ImageIcon(this::class.java.getResource(name)).image
    }

//    fun moveToScreenCenter() {
//        val screenSize = Toolkit.getDefaultToolkit().screenSize
//        playerFrame.location = Point(
//            screenSize.width / 2 - playerFrame.width / 2,
//            screenSize.height / 2 - playerFrame.height / 2,
//        )
//    }
//
//    fun getFrameCentre(): Point {
//        return Point(playerFrame.x + playerFrame.width / 2, playerFrame.y + playerFrame.height / 2)
//    }
//
//    fun setFullscreenMode() {
//        GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.fullScreenWindow = playerFrame
//    }
//
//    fun setWindowedMode() {
//        GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.fullScreenWindow = null
//    }
//
//    fun changeFrameSize(winWidth: Int, winHeight: Int) {
//        playerFrame.contentPane.apply {
//            preferredSize = Dimension(winWidth, winHeight)
//        }
//    }
//
//    fun setCurrentCursor(cursor: Cursor) {
//        currentCursor = cursor
//        playerFrame.contentPane.cursor = cursor
//    }
//
//    fun setInvisibleCursor() {
//        playerFrame.contentPane.cursor = blankCursor
//    }
//
//    fun setVisibleCursor() {
//        playerFrame.contentPane.cursor = currentCursor
//    }
//
//    fun startFpsCounter() {
//        camera.fpsCounter.start()
//    }
//
//    fun stopFpsCounter() {
//        camera.fpsCounter.stop()
//    }
//
//    fun addComponents() {
//        camera.addComponents()
//    }
//
//    fun setCameraLayers(cameraLayers: CameraLayers) {
//        camera.setCameraLayers(cameraLayers)
//    }
//
//    fun enableDebugVision() {
//        camera.enableDebugVision()
//    }
//
//    fun disableDebugVision() {
//        camera.disableDebugVision()
//    }
}
