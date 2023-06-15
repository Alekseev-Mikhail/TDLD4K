package tdld4k.controllers

import tdld4k.GameSingleClient
import tdld4k.player.GamePlayer
import java.awt.MouseInfo
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.MouseEvent
import java.io.File
import javax.imageio.ImageIO

abstract class GameRotationControl(
    private val player: GamePlayer,
    private val moveMouseWithRobotInput: GameMoveMouseWithRobotInput,
    singleClient: GameSingleClient,
) : GameMoveMouseWithRobot(singleClient, moveMouseWithRobotInput) {
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