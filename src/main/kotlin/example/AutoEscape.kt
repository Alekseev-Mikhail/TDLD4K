package example

import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent

class AutoEscape(private val keyboardController: KeyboardController) : FocusAdapter() {
    override fun focusLost(e: FocusEvent?) {
        keyboardController.menuOn()
    }
}
