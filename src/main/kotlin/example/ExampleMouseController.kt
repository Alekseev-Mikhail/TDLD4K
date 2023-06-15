package example

import tdld4k.GameSingleClient
import tdld4k.controllers.GameMoveMouseWithRobotInput
import tdld4k.controllers.GameRotationControl
import tdld4k.player.GamePlayer
import java.awt.Cursor
import java.awt.MouseInfo

class ExampleMouseController(
    private var singleClient: GameSingleClient,
    player: GamePlayer,
    moveMouseWithRobotInput: GameMoveMouseWithRobotInput,
) : GameRotationControl(singleClient, player, moveMouseWithRobotInput) {
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
