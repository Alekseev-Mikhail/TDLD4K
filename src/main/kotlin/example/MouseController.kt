package example

import tdld4k.Client
import tdld4k.controllers.MouseMove
import tdld4k.controllers.RotationControl
import tdld4k.player.Player
import java.awt.Cursor
import java.awt.MouseInfo

class MouseController(
    private var client: Client,
    player: Player,
    mouseMove: MouseMove,
) : RotationControl(client, player, mouseMove) {
    var firstCustomCursor: Cursor = Cursor.getDefaultCursor()
    var secondCustomCursor: Cursor = Cursor.getDefaultCursor()

    override fun mouseMovedInFreeze() {
//        if (MouseInfo.getPointerInfo().location.y > client.getFrameCentre().y) {
//            client.setCurrentCursor(firstCustomCursor)
//        } else {
//            client.setCurrentCursor(secondCustomCursor)
//        }
    }
}
