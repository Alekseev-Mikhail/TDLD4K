package example

import tdld4k.Client
import tdld4k.controllers.MouseMove
import tdld4k.controllers.MovementControl
import tdld4k.world.World
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_DOWN
import java.awt.event.KeyEvent.VK_ESCAPE
import java.awt.event.KeyEvent.VK_F11
import java.awt.event.KeyEvent.VK_F2
import java.awt.event.KeyEvent.VK_F3
import java.awt.event.KeyEvent.VK_UP
import javax.swing.Timer

class KeyboardController(
    world: World,
    forward: Int,
    back: Int,
    left: Int,
    right: Int,
    private val client: Client,
    private val player: ExamplePlayer,
    private val cameraLayers: Menus,
    private val mouseMove: MouseMove,
) : MovementControl(
    world,
    player,
    forward,
    back,
    left,
    right,
) {
    private val yUp = Timer(1_000 / player.maxFps) {
        player.y = player.y + 0.01
    }
    private val yDown = Timer(1_000 / player.maxFps) {
        player.y = player.y - 0.01
    }

    override fun keyPressed(e: KeyEvent) {
        super.keyPressed(e)
        when (e.keyCode) {
            VK_F3 -> player.isShowDebugMenu = player.isShowDebugMenu == false

            VK_F2 -> debugVision()

            VK_ESCAPE -> if (!player.isEscape) menuOn() else menuOff()

            VK_F11 -> fullscreen()

            VK_UP -> yUp.start()

            VK_DOWN -> yDown.start()
        }
    }

    fun menuOn() {
        player.isEscape = true
        player.isFreezeRotation = true
        player.isFreezeMovement = true
        mouseMove.isFreezeMove = true
        cameraLayers.sliders.forEach { e ->
            e.isVisible = true
        }
        cameraLayers.exitButton.isVisible = true
//        client.setVisibleCursor()
//        client.stopFpsCounter()
        moveToForward.stop()
        moveToBack.stop()
        moveToLeft.stop()
        moveToRight.stop()
    }

    private fun menuOff() {
        player.isEscape = false
        player.isFreezeRotation = false
        player.isFreezeMovement = false
        mouseMove.isFreezeMove = false
        cameraLayers.sliders.forEach { e ->
            e.isVisible = false
        }
        cameraLayers.exitButton.isVisible = false
//        client.setInvisibleCursor()
//        client.startFpsCounter()
    }

    private fun debugVision() {
        if (!player.isDebugVision) {
            player.isDebugVision = true
//            client.enableDebugVision()
        } else {
            player.isDebugVision = false
//            client.disableDebugVision()
        }
    }

    private fun fullscreen() {
        if (player.isFullscreen) {
            player.isFullscreen = false
//            client.setWindowedMode()
//            client.playerFrame.pack()
//            client.moveToScreenCenter()
        } else {
            player.isFullscreen = true
//            client.setFullscreenMode()
        }
    }

    override fun keyReleased(e: KeyEvent) {
        super.keyReleased(e)
        when (e.keyCode) {
            VK_UP -> {
                yUp.stop()
            }

            VK_DOWN -> {
                yDown.stop()
            }
        }
    }
}
