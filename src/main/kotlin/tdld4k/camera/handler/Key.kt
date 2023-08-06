package tdld4k.camera.handler

import org.lwjgl.glfw.GLFW.GLFW_KEY_0
import org.lwjgl.glfw.GLFW.GLFW_KEY_1
import org.lwjgl.glfw.GLFW.GLFW_KEY_2
import org.lwjgl.glfw.GLFW.GLFW_KEY_3
import org.lwjgl.glfw.GLFW.GLFW_KEY_4
import org.lwjgl.glfw.GLFW.GLFW_KEY_5
import org.lwjgl.glfw.GLFW.GLFW_KEY_6
import org.lwjgl.glfw.GLFW.GLFW_KEY_7
import org.lwjgl.glfw.GLFW.GLFW_KEY_8
import org.lwjgl.glfw.GLFW.GLFW_KEY_9
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_APOSTROPHE
import org.lwjgl.glfw.GLFW.GLFW_KEY_B
import org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSLASH
import org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE
import org.lwjgl.glfw.GLFW.GLFW_KEY_C
import org.lwjgl.glfw.GLFW.GLFW_KEY_CAPS_LOCK
import org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA
import org.lwjgl.glfw.GLFW.GLFW_KEY_D
import org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE
import org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN
import org.lwjgl.glfw.GLFW.GLFW_KEY_E
import org.lwjgl.glfw.GLFW.GLFW_KEY_END
import org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER
import org.lwjgl.glfw.GLFW.GLFW_KEY_EQUAL
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.glfw.GLFW.GLFW_KEY_F
import org.lwjgl.glfw.GLFW.GLFW_KEY_F1
import org.lwjgl.glfw.GLFW.GLFW_KEY_F10
import org.lwjgl.glfw.GLFW.GLFW_KEY_F11
import org.lwjgl.glfw.GLFW.GLFW_KEY_F12
import org.lwjgl.glfw.GLFW.GLFW_KEY_F2
import org.lwjgl.glfw.GLFW.GLFW_KEY_F3
import org.lwjgl.glfw.GLFW.GLFW_KEY_F4
import org.lwjgl.glfw.GLFW.GLFW_KEY_F5
import org.lwjgl.glfw.GLFW.GLFW_KEY_F6
import org.lwjgl.glfw.GLFW.GLFW_KEY_F7
import org.lwjgl.glfw.GLFW.GLFW_KEY_F8
import org.lwjgl.glfw.GLFW.GLFW_KEY_F9
import org.lwjgl.glfw.GLFW.GLFW_KEY_G
import org.lwjgl.glfw.GLFW.GLFW_KEY_GRAVE_ACCENT
import org.lwjgl.glfw.GLFW.GLFW_KEY_H
import org.lwjgl.glfw.GLFW.GLFW_KEY_HOME
import org.lwjgl.glfw.GLFW.GLFW_KEY_I
import org.lwjgl.glfw.GLFW.GLFW_KEY_INSERT
import org.lwjgl.glfw.GLFW.GLFW_KEY_J
import org.lwjgl.glfw.GLFW.GLFW_KEY_K
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_0
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_1
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_2
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_3
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_4
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_5
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_6
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_7
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_8
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_9
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ADD
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DECIMAL
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DIVIDE
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_EQUAL
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_MULTIPLY
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_SUBTRACT
import org.lwjgl.glfw.GLFW.GLFW_KEY_L
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_BRACKET
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_M
import org.lwjgl.glfw.GLFW.GLFW_KEY_MINUS
import org.lwjgl.glfw.GLFW.GLFW_KEY_N
import org.lwjgl.glfw.GLFW.GLFW_KEY_NUM_LOCK
import org.lwjgl.glfw.GLFW.GLFW_KEY_O
import org.lwjgl.glfw.GLFW.GLFW_KEY_P
import org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN
import org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP
import org.lwjgl.glfw.GLFW.GLFW_KEY_PAUSE
import org.lwjgl.glfw.GLFW.GLFW_KEY_PERIOD
import org.lwjgl.glfw.GLFW.GLFW_KEY_PRINT_SCREEN
import org.lwjgl.glfw.GLFW.GLFW_KEY_Q
import org.lwjgl.glfw.GLFW.GLFW_KEY_R
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_ALT
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_BRACKET
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_S
import org.lwjgl.glfw.GLFW.GLFW_KEY_SCROLL_LOCK
import org.lwjgl.glfw.GLFW.GLFW_KEY_SEMICOLON
import org.lwjgl.glfw.GLFW.GLFW_KEY_SLASH
import org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE
import org.lwjgl.glfw.GLFW.GLFW_KEY_T
import org.lwjgl.glfw.GLFW.GLFW_KEY_TAB
import org.lwjgl.glfw.GLFW.GLFW_KEY_U
import org.lwjgl.glfw.GLFW.GLFW_KEY_UP
import org.lwjgl.glfw.GLFW.GLFW_KEY_V
import org.lwjgl.glfw.GLFW.GLFW_KEY_W
import org.lwjgl.glfw.GLFW.GLFW_KEY_X
import org.lwjgl.glfw.GLFW.GLFW_KEY_Y
import org.lwjgl.glfw.GLFW.GLFW_KEY_Z
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFW.GLFW_REPEAT

enum class Key(val value: Int) {
    KEY_RELEASE(GLFW_RELEASE),
    KEY_PRESS(GLFW_PRESS),
    KEY_REPEAT(GLFW_REPEAT),

    KEY_SPACE(GLFW_KEY_SPACE),
    KEY_APOSTROPHE(GLFW_KEY_APOSTROPHE),
    KEY_COMMA(GLFW_KEY_COMMA),
    KEY_MINUS(GLFW_KEY_MINUS),
    KEY_PERIOD(GLFW_KEY_PERIOD),
    KEY_SLASH(GLFW_KEY_SLASH),
    KEY_0(GLFW_KEY_0),
    KEY_1(GLFW_KEY_1),
    KEY_2(GLFW_KEY_2),
    KEY_3(GLFW_KEY_3),
    KEY_4(GLFW_KEY_4),
    KEY_5(GLFW_KEY_5),
    KEY_6(GLFW_KEY_6),
    KEY_7(GLFW_KEY_7),
    KEY_8(GLFW_KEY_8),
    KEY_9(GLFW_KEY_9),
    KEY_SEMICOLON(GLFW_KEY_SEMICOLON),
    KEY_EQUAL(GLFW_KEY_EQUAL),
    KEY_A(GLFW_KEY_A),
    KEY_B(GLFW_KEY_B),
    KEY_C(GLFW_KEY_C),
    KEY_D(GLFW_KEY_D),
    KEY_E(GLFW_KEY_E),
    KEY_F(GLFW_KEY_F),
    KEY_G(GLFW_KEY_G),
    KEY_H(GLFW_KEY_H),
    KEY_I(GLFW_KEY_I),
    KEY_J(GLFW_KEY_J),
    KEY_K(GLFW_KEY_K),
    KEY_L(GLFW_KEY_L),
    KEY_M(GLFW_KEY_M),
    KEY_N(GLFW_KEY_N),
    KEY_O(GLFW_KEY_O),
    KEY_P(GLFW_KEY_P),
    KEY_Q(GLFW_KEY_Q),
    KEY_R(GLFW_KEY_R),
    KEY_S(GLFW_KEY_S),
    KEY_T(GLFW_KEY_T),
    KEY_U(GLFW_KEY_U),
    KEY_V(GLFW_KEY_V),
    KEY_W(GLFW_KEY_W),
    KEY_X(GLFW_KEY_X),
    KEY_Y(GLFW_KEY_Y),
    KEY_Z(GLFW_KEY_Z),
    KEY_LEFT_BRACKET(GLFW_KEY_LEFT_BRACKET),
    KEY_BACKSLASH(GLFW_KEY_BACKSLASH),
    KEY_RIGHT_BRACKET(GLFW_KEY_RIGHT_BRACKET),
    KEY_GRAVE_ACCENT(GLFW_KEY_GRAVE_ACCENT),

    KEY_ESCAPE(GLFW_KEY_ESCAPE),
    KEY_ENTER(GLFW_KEY_ENTER),
    KEY_TAB(GLFW_KEY_TAB),
    KEY_BACKSPACE(GLFW_KEY_BACKSPACE),
    KEY_INSERT(GLFW_KEY_INSERT),
    KEY_DELETE(GLFW_KEY_DELETE),
    KEY_RIGHT(GLFW_KEY_RIGHT),
    KEY_LEFT(GLFW_KEY_LEFT),
    KEY_DOWN(GLFW_KEY_DOWN),
    KEY_UP(GLFW_KEY_UP),
    KEY_PAGE_UP(GLFW_KEY_PAGE_UP),
    KEY_PAGE_DOWN(GLFW_KEY_PAGE_DOWN),
    KEY_HOME(GLFW_KEY_HOME),
    KEY_END(GLFW_KEY_END),
    KEY_CAPS_LOCK(GLFW_KEY_CAPS_LOCK),
    KEY_SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK),
    KEY_NUM_LOCK(GLFW_KEY_NUM_LOCK),
    KEY_PRINT_SCREEN(GLFW_KEY_PRINT_SCREEN),
    KEY_PAUSE(GLFW_KEY_PAUSE),
    KEY_F1(GLFW_KEY_F1),
    KEY_F2(GLFW_KEY_F2),
    KEY_F3(GLFW_KEY_F3),
    KEY_F4(GLFW_KEY_F4),
    KEY_F5(GLFW_KEY_F5),
    KEY_F6(GLFW_KEY_F6),
    KEY_F7(GLFW_KEY_F7),
    KEY_F8(GLFW_KEY_F8),
    KEY_F9(GLFW_KEY_F9),
    KEY_F10(GLFW_KEY_F10),
    KEY_F11(GLFW_KEY_F11),
    KEY_F12(GLFW_KEY_F12),
    KEY_KP_0(GLFW_KEY_KP_0),
    KEY_KP_1(GLFW_KEY_KP_1),
    KEY_KP_2(GLFW_KEY_KP_2),
    KEY_KP_3(GLFW_KEY_KP_3),
    KEY_KP_4(GLFW_KEY_KP_4),
    KEY_KP_5(GLFW_KEY_KP_5),
    KEY_KP_6(GLFW_KEY_KP_6),
    KEY_KP_7(GLFW_KEY_KP_7),
    KEY_KP_8(GLFW_KEY_KP_8),
    KEY_KP_9(GLFW_KEY_KP_9),
    KEY_KP_DECIMAL(GLFW_KEY_KP_DECIMAL),
    KEY_KP_DIVIDE(GLFW_KEY_KP_DIVIDE),
    KEY_KP_MULTIPLY(GLFW_KEY_KP_MULTIPLY),
    KEY_KP_SUBTRACT(GLFW_KEY_KP_SUBTRACT),
    KEY_KP_ADD(GLFW_KEY_KP_ADD),
    KEY_KP_ENTER(GLFW_KEY_KP_ENTER),
    KEY_KP_EQUAL(GLFW_KEY_KP_EQUAL),
    KEY_LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT),
    KEY_LEFT_CONTROL(GLFW_KEY_LEFT_CONTROL),
    KEY_LEFT_ALT(GLFW_KEY_LEFT_ALT),
    KEY_RIGHT_SHIFT(GLFW_KEY_RIGHT_SHIFT),
    KEY_RIGHT_CONTROL(GLFW_KEY_RIGHT_CONTROL),
    KEY_RIGHT_ALT(GLFW_KEY_RIGHT_ALT),
}
