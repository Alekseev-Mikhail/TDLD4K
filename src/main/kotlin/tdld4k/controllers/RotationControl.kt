package tdld4k.controllers

import tdld4k.SingleClient
import tdld4k.player.Player
import java.awt.MouseInfo
import java.awt.event.MouseEvent

abstract class RotationControl(
    singleClient: SingleClient,
    private val player: Player,
    private val moveMouseWithRobotInput: MoveMouseWithRobotInput,
) : MoveMouseWithRobot(singleClient, moveMouseWithRobotInput) {
    private var lastX = MouseInfo.getPointerInfo().location.x
    private var lastY = MouseInfo.getPointerInfo().location.y

    override fun mouseMoved(e: MouseEvent) {
        super.mouseMoved(e)
        player.isFreezeRotation
        if (!player.isFreezeRotation) {
            if (moveMouseWithRobotInput.isRobot) {
                player.direction = player.direction - player.rotationSpeed * (lastX - e.x)
                player.directionY = player.directionY - 0.01 * (e.y - lastY)
                if (player.direction >= 360) {
                    player.direction = 0.0
                } else if (player.direction < 0) {
                    player.direction = 360.0
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
