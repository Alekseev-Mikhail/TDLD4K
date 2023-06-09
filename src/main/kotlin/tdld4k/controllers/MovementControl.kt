package tdld4k.controllers

import tdld4k.math.RayHandle
import tdld4k.player.Player
import tdld4k.world.World
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.Timer

abstract class MovementControl(
    world: World,
    private val player: Player,
    private val forward: Int,
    private val back: Int,
    private val left: Int,
    private val right: Int,
) : KeyAdapter() {
    private val rayHandle = RayHandle(world, player)

    val moveToForward = Timer(1_000 / player.maxFps) {
        val point = rayHandle.ray(player.movementSpeed, Math.toRadians(player.xDirection))
        player.x = point.rayPoint.x
        player.z = point.rayPoint.y
    }

    val moveToBack = Timer(1_000 / player.maxFps) {
        val point = rayHandle.ray(player.movementSpeed, Math.toRadians(player.xDirection + 180))
        player.x = point.rayPoint.x
        player.z = point.rayPoint.y
    }

    val moveToLeft = Timer(1_000 / player.maxFps) {
        val point = rayHandle.ray(player.movementSpeed, Math.toRadians(player.xDirection - 90))
        player.x = point.rayPoint.x
        player.z = point.rayPoint.y
    }

    val moveToRight = Timer(1_000 / player.maxFps) {
        val point = rayHandle.ray(player.movementSpeed, Math.toRadians(player.xDirection + 90))
        player.x = point.rayPoint.x
        player.z = point.rayPoint.y
    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            forward -> if (!player.isFreezeMovement) moveToForward.start()
            back -> if (!player.isFreezeMovement) moveToBack.start()
            left -> if (!player.isFreezeMovement) moveToLeft.start()
            right -> if (!player.isFreezeMovement) moveToRight.start()
        }
    }

    override fun keyReleased(e: KeyEvent) {
        when (e.keyCode) {
            forward -> moveToForward.stop()
            back -> moveToBack.stop()
            left -> moveToLeft.stop()
            right -> moveToRight.stop()
        }
    }
}
