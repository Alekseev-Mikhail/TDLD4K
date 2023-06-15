package example

import tdld4k.SingleClient
import tdld4k.controllers.MoveMouseWithRobotInput
import tdld4k.controllers.RotationControl
import tdld4k.player.Player
import java.awt.Cursor
import java.awt.MouseInfo

class MouseController(
    private var singleClient: SingleClient,
    player: Player,
    moveMouseWithRobotInput: MoveMouseWithRobotInput,
) : RotationControl(singleClient, player, moveMouseWithRobotInput) {
    var firstCustomCursor: Cursor = Cursor.getDefaultCursor()
    var secondCustomCursor: Cursor = Cursor.getDefaultCursor()

    override fun mouseMovedInFreeze() {
        if (MouseInfo.getPointerInfo().location.y > singleClient.getFrameCentre().y) {
            singleClient.setCurrentCursor(firstCustomCursor)
        } else {
            singleClient.setCurrentCursor(secondCustomCursor)
        }
    }
}
