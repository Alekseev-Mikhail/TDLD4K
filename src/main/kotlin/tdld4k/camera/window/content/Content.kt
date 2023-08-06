package tdld4k.camera.window.content

import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_QUADS
import org.lwjgl.opengl.GL11.glBegin
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.GL11.glColor4f
import org.lwjgl.opengl.GL11.glEnd
import org.lwjgl.opengl.GL11.glVertex2d
import org.lwjgl.opengl.GL11.glViewport
import tdld4k.camera.window.Window
import tdld4k.util.BLACK
import tdld4k.util.Paint
import tdld4k.util.WHITE
import tdld4k.util.geometry.PointD
import tdld4k.util.geometry.Quad

private var lastId = -1

abstract class Content {
    val id = generateId()
    open val isMain: Boolean = false

    protected var paint: Paint = BLACK
        set(value) {
            glColor4f(value.red.toFloat(), value.green.toFloat(), value.blue.toFloat(), value.alpha.toFloat())
            field = value
        }

    protected var background: Paint = WHITE
        set(value) {
            glClearColor(value.red.toFloat(), value.green.toFloat(), value.blue.toFloat(), value.alpha.toFloat())
            field = value
        }

    private lateinit var window: Window

    fun create() {
        createCapabilities()
    }

    open fun render(window: Window) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        this.window = window
    }

    open fun changeOn(window: Window) {
        glClearColor(
            background.red.toFloat(),
            background.green.toFloat(),
            background.blue.toFloat(),
            background.alpha.toFloat(),
        )
    }

    fun setSize(width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    protected fun drawRectangle(x: Double, y: Double, width: Double, height: Double) {
        drawQuad(
            PointD(x, y),
            PointD(x + width, y),
            PointD(x + width, y + height),
            PointD(x, y + height),
        )
    }

    protected fun drawQuad(quad: Quad) {
        drawQuad(quad.first, quad.second, quad.third, quad.fourth)
    }

    protected fun drawQuad(first: PointD, second: PointD, third: PointD, fourth: PointD) {
        glBegin(GL_QUADS)
        glVertex2d(translateX(first.x), translateY(first.y))
        glVertex2d(translateX(second.x), translateY(second.y))
        glVertex2d(translateX(third.x), translateY(third.y))
        glVertex2d(translateX(fourth.x), translateY(fourth.y))
        glEnd()
    }

    private fun translateX(x: Double): Double {
        val widthHalf = window.width.toFloat() / 2
        val displaced = x - widthHalf
        return displaced / widthHalf
    }

    private fun translateY(y: Double): Double {
        val heightHalf = window.height.toFloat() / 2
        val displaced = heightHalf - y
        return displaced / heightHalf
    }
}

private fun generateId(): Int {
    lastId++
    return lastId
}
