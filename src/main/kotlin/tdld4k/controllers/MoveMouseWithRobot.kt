package tdld4k.controllers

import tdld4k.Client
import java.awt.Robot
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener

abstract class MoveMouseWithRobot(
    client: Client,
    private var mouseMove: MouseMove,
) : MouseMotionListener {
    private val robot = Robot()
//    private val playerFrame = client.playerFrame

    override fun mouseMoved(e: MouseEvent) {
        if (!mouseMove.isFreezeMove) {
            if (!mouseMove.isRobot) {
                mouseMove.isRobot = true
//                robot.mouseMove(playerFrame.x + playerFrame.width / 2, playerFrame.y + playerFrame.height / 2)
            } else {
                mouseMove.isRobot = false
            }
        }
    }

    override fun mouseDragged(e: MouseEvent?) {
    }
}
