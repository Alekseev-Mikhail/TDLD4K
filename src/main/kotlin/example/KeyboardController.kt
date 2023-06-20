package example

import tdld4k.SingleClient
import tdld4k.controllers.MoveMouseWithRobotInput
import tdld4k.controllers.MovementControl
import tdld4k.player.Player
import tdld4k.world.World
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_DOWN
import java.awt.event.KeyEvent.VK_END
import java.awt.event.KeyEvent.VK_ESCAPE
import java.awt.event.KeyEvent.VK_F11
import java.awt.event.KeyEvent.VK_F3
import java.awt.event.KeyEvent.VK_HOME
import java.awt.event.KeyEvent.VK_LEFT
import java.awt.event.KeyEvent.VK_RIGHT
import java.awt.event.KeyEvent.VK_UP
import javax.swing.Timer

class KeyboardController(
    world: World,
    forward: Int,
    back: Int,
    left: Int,
    right: Int,
    private val singleClient: SingleClient,
    private val player: Player,
    private var isFullscreen: Boolean,
    private val moveMouseWithRobotInput: MoveMouseWithRobotInput,
) : MovementControl(
    world,
    player,
    forward,
    back,
    left,
    right,
) {
    private var isEscape = false
    private val fovUp = Timer(1_000 / player.maxFps) {
        player.fov++
    }
    private val fovDown = Timer(1_000 / player.maxFps) {
        if (player.fov > 0.0) {
            player.fov--
        }
    }
    private val yUp = Timer(1_000 / player.maxFps) {
        player.y = player.y + 0.01
    }
    private val yDown = Timer(1_000 / player.maxFps) {
        player.y = player.y - 0.01
    }
    private val qualityUp = Timer(1_000 / player.maxFps) {
        player.quality = player.quality * 1.01
    }
    private val qualityDown = Timer(1_000 / player.maxFps) {
        if (player.quality > 0.0) {
            player.quality = player.quality / 1.01
        }
    }

    override fun keyPressed(e: KeyEvent) {
        super.keyPressed(e)
        when (e.keyCode) {
            VK_F3 -> player.isShowDebugMenu = player.isShowDebugMenu == false

            VK_ESCAPE -> {
                if (!isEscape) {
                    isEscape = true
                    player.isFreezeRotation = true
                    player.isFreezeMovement = true
                    moveMouseWithRobotInput.isFreezeMove = true
                    singleClient.setVisibleCursor()
                    singleClient.stopFpsCounter()
                    moveToForward.stop()
                    moveToBack.stop()
                    moveToLeft.stop()
                    moveToRight.stop()
                } else {
                    isEscape = false
                    player.isFreezeRotation = false
                    player.isFreezeMovement = false
                    moveMouseWithRobotInput.isFreezeMove = false
                    singleClient.setInvisibleCursor()
                    singleClient.startFpsCounter()
                }
            }

            VK_F11 -> {
                if (isFullscreen) {
                    isFullscreen = false
                    singleClient.setWindowedMode()
                    singleClient.playerFrame.pack()
                    singleClient.moveToScreenCenter()
                } else {
                    isFullscreen = true
                    singleClient.setFullscreenMode()
                }
            }

            VK_LEFT -> {
                fovUp.start()
            }

            VK_RIGHT -> {
                fovDown.start()
            }

            VK_UP -> {
                yUp.start()
            }

            VK_DOWN -> {
                yDown.start()
            }

            VK_HOME -> {
                qualityUp.start()
            }

            VK_END -> {
                qualityDown.start()
            }
        }
    }

    override fun keyReleased(e: KeyEvent) {
        super.keyReleased(e)
        when (e.keyCode) {
            VK_LEFT -> {
                fovUp.stop()
            }

            VK_RIGHT -> {
                fovDown.stop()
            }

            VK_UP -> {
                yUp.stop()
            }

            VK_DOWN -> {
                yDown.stop()
            }

            VK_HOME -> {
                qualityUp.stop()
            }

            VK_END -> {
                qualityDown.stop()
            }
        }
    }
}
