package example.controller

import example.gui.EscapeGUI
import example.gui.MainGUI
import tdld4k.camera.handler.Key.KEY_ESCAPE
import tdld4k.camera.handler.keyboard.KeyAction
import tdld4k.camera.handler.keyboard.Movement
import tdld4k.entity.Creature
import tdld4k.options.MovementOptions
import tdld4k.world.World

class KeyboardController(
    override val world: World,
    override val creature: Creature,
    override val options: MovementOptions,
    private val mainGUI: MainGUI,
    private val escapeGUI: EscapeGUI,
) : Movement() {
    override fun keyPress(action: KeyAction) {
        super.keyPress(action)
        when (action.window.content.id) {
            mainGUI.id -> {
                when (action.key) {
                    KEY_ESCAPE.value -> action.window.content = escapeGUI
                    else -> {}
                }
            }

            escapeGUI.id -> {
                when (action.key) {
                    KEY_ESCAPE.value -> action.window.content = mainGUI
                    else -> {}
                }
            }
        }
    }
}
