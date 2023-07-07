package tdld4k.controllers

import tdld4k.Client
import tdld4k.player.Player
import java.awt.MouseInfo
import java.awt.event.MouseEvent

abstract class RotationControl(
    client: Client,
    private val player: Player,
    private val mouseMove: MouseMove,
) : MoveMouseWithRobot(client, mouseMove) {
    private var lastX = MouseInfo.getPointerInfo().location.x
    private var lastY = MouseInfo.getPointerInfo().location.y

    override fun mouseMoved(e: MouseEvent) {
        super.mouseMoved(e)
        player.isFreezeRotation
        if (!player.isFreezeRotation) {
            if (mouseMove.isRobot) {
                player.xDirection = player.xDirection - player.xRotationSpeed * (lastX - e.x)
                player.yDirection = player.yDirection - player.yRotationSpeed * (e.y - lastY)
                if (player.xDirection >= 360) {
                    player.xDirection = 0.0
                } else if (player.xDirection < 0) {
                    player.xDirection = 360.0
                }
            }
        } else {
            mouseMovedInFreeze()
        }
        lastX = e.x
        lastY = e.y
    }

    abstract fun mouseMovedInFreeze()
}
