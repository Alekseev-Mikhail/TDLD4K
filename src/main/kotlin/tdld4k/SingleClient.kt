package tdld4k

import tdld4k.player.Player
import tdld4k.player.camera.Camera
import tdld4k.player.camera.CameraLayers
import tdld4k.world.World
import java.awt.Cursor
import java.awt.Dimension
import java.awt.GraphicsEnvironment
import java.awt.Image
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.FocusListener
import java.awt.event.KeyListener
import java.awt.event.MouseMotionListener
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE

class SingleClient(
    world: World,
    private val player: Player,
) {
    val version: String? = this::class.java.`package`.implementationVersion
    var playerFrame: JFrame = JFrame()
    private val camera = Camera(world, player)
    private var currentCursor = Cursor.getDefaultCursor()
    private val blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB),
        Point(0, 0),
        "blank cursor",
    )

    companion object {
        fun getImage(name: String): Image = ImageIcon(SingleClient::class.java.getResource(name)).image
    }

    fun initializationFrame(
        keyboardController: KeyListener? = null,
        mouseController: MouseMotionListener? = null,
        focusController: FocusListener? = null,
    ) {
        playerFrame.contentPane = camera.apply {
            player.addListener { repaint() }
        }
        playerFrame.addKeyListener(keyboardController)
        playerFrame.addMouseMotionListener(mouseController)
        playerFrame.addFocusListener(focusController)
        playerFrame.defaultCloseOperation = EXIT_ON_CLOSE
    }

    fun moveToScreenCenter() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        playerFrame.location = Point(
            screenSize.width / 2 - playerFrame.width / 2,
            screenSize.height / 2 - playerFrame.height / 2,
        )
    }

    fun getFrameCentre(): Point {
        return Point(playerFrame.x + playerFrame.width / 2, playerFrame.y + playerFrame.height / 2)
    }

    fun setFullscreenMode() {
        GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.fullScreenWindow = playerFrame
    }

    fun setWindowedMode() {
        GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.fullScreenWindow = null
    }

    fun changeFrameSize(winWidth: Int, winHeight: Int) {
        playerFrame.contentPane.apply {
            preferredSize = Dimension(winWidth, winHeight)
        }
    }

    fun setCurrentCursor(cursor: Cursor) {
        currentCursor = cursor
        playerFrame.contentPane.cursor = cursor
    }

    fun setInvisibleCursor() {
        playerFrame.contentPane.cursor = blankCursor
    }

    fun setVisibleCursor() {
        playerFrame.contentPane.cursor = currentCursor
    }

    fun startFpsCounter() {
        camera.fpsCounter.start()
    }

    fun stopFpsCounter() {
        camera.fpsCounter.stop()
    }

    fun addComponents() {
        camera.addComponents()
    }

    fun setCameraLayers(cameraLayers: CameraLayers) {
        camera.setCameraLayers(cameraLayers)
    }
}
