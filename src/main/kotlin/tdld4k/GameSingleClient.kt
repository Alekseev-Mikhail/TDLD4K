package tdld4k

import tdld4k.player.GameCamera
import tdld4k.player.GamePlayer
import tdld4k.world.GameWorld
import java.awt.*
import java.awt.event.KeyListener
import java.awt.event.MouseMotionListener
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE

class GameSingleClient(
    private val player: GamePlayer,
    world: GameWorld,
) {
    var playerFrame: JFrame = JFrame()
    private val camera = GameCamera(world, player)
    private var currentCursor = Cursor.getDefaultCursor()
    private val blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), Point(0, 0), "blank cursor"
    )

    fun initializationFrame(keyboardController: KeyListener, mouseController: MouseMotionListener) {
        playerFrame.contentPane = camera.apply {
            player.addListener { repaint() }
        }
        playerFrame.addKeyListener(keyboardController)
        playerFrame.addMouseMotionListener(mouseController)
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

    fun startGame() {}

    fun pauseGame() {}

    fun resumeGame() {}

    fun stopGame() {}

    fun killGame() {}
}