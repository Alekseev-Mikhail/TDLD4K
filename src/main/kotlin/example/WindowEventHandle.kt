package example

import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose
import tdld4k.ClientLife
import tdld4k.Window

class WindowEventHandle : ClientLife {
    override fun born(window: Window) {
        glfwSetKeyCallback(window.windowId) { _, key, _, action, _ ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                exitCallback(window.windowId)
            }
        }
    }

    private fun exitCallback(windowId: Long) {
        glfwSetWindowShouldClose(windowId, true)
    }
}
