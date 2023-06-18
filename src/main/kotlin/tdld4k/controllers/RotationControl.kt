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

    override fun mouseMoved(e: MouseEvent) {
        super.mouseMoved(e)
        player.isFreezeRotation
        if (!player.isFreezeRotation) {
            if (moveMouseWithRobotInput.isRobot) {
                player.direction = player.direction - player.rotationSpeed * (lastX - e.x)
            }
        } else {
            mouseMovedInFreeze()
        }
        lastX = e.x
    }

    abstract fun mouseMovedInFreeze()
}