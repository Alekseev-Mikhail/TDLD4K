package example.controller

import example.gui.EscapeGUI
import tdld4k.camera.handler.mouse.MouseMotion
import tdld4k.camera.handler.mouse.Rotation
import tdld4k.entity.Creature
import tdld4k.util.Cursor

class MouseController(
    override val creature: Creature,
    private val escapeGUI: EscapeGUI,
    private val happyCursor: Cursor,
    private val sadCursor: Cursor,
) : Rotation() {
    override fun motion(motion: MouseMotion) {
        super.motion(motion)
        if (motion.window.content.id == escapeGUI.id) {
            if (motion.y > motion.window.height / 2) {
                if (motion.window.cursor == sadCursor) {
                    motion.window.cursor = happyCursor
                }
            } else {
                if (motion.window.cursor == happyCursor) {
                    motion.window.cursor = sadCursor
                }
            }
        }
    }
}
