package tdld4k.controllers

import tdld4k.GameSingleClient
import java.awt.Frame
import java.awt.Robot
import java.awt.Toolkit
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener
import javax.swing.JFrame

abstract class GameMoveMouseWithRobot(
    private val singleClient: GameSingleClient,
    private var moveMouseWithRobotInput: GameMoveMouseWithRobotInput,
) : MouseMotionListener {
    private val robot = Robot()
    private val playerFrame = singleClient.playerFrame

    override fun mouseMoved(e: MouseEvent) {
        if (!moveMouseWithRobotInput.isFreezeMove) {
            if (!moveMouseWithRobotInput.isRobot) {
                moveMouseWithRobotInput.isRobot = true
                robot.mouseMove(playerFrame.x + playerFrame.width / 2, playerFrame.y + playerFrame.height / 2)
            } else {
                moveMouseWithRobotInput.isRobot = false
            }
        }
    }

    override fun mouseDragged(e: MouseEvent?) {

    }
}