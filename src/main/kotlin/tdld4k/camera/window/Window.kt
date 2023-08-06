package tdld4k.camera.window

import org.lwjgl.BufferUtils.createByteBuffer
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.GLFW_CURSOR
import org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED
import org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL
import org.lwjgl.glfw.GLFW.glfwCreateCursor
import org.lwjgl.glfw.GLFW.glfwCreateWindow
import org.lwjgl.glfw.GLFW.glfwDefaultWindowHints
import org.lwjgl.glfw.GLFW.glfwDestroyWindow
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwSetCursor
import org.lwjgl.glfw.GLFW.glfwSetCursorPos
import org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback
import org.lwjgl.glfw.GLFW.glfwSetErrorCallback
import org.lwjgl.glfw.GLFW.glfwSetInputMode
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowIcon
import org.lwjgl.glfw.GLFW.glfwSetWindowPos
import org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowSize
import org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.glfw.GLFW.glfwTerminate
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose
import org.lwjgl.glfw.GLFWImage.malloc
import org.lwjgl.stb.STBImage.stbi_image_free
import org.lwjgl.stb.STBImage.stbi_load_from_memory
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.system.MemoryUtil.memAllocInt
import org.lwjgl.system.MemoryUtil.memFree
import org.lwjgl.system.MemoryUtil.memUTF8
import org.slf4j.LoggerFactory
import tdld4k.camera.window.content.Content
import tdld4k.camera.window.content.EmptyContent
import tdld4k.util.Cursor
import java.lang.Thread.currentThread
import java.nio.ByteBuffer
import java.nio.channels.Channels.newChannel
import java.nio.file.Files.isReadable
import java.nio.file.Files.newByteChannel
import java.nio.file.Paths

data class Window(private val windowData: WindowData) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    val id
        get() = windowData.id
    var content: Content = EmptyContent()
        set(value) {
            value.changeOn(this)
            field = value
            logger.info("Content set successful. Id: ${content.id}")
        }

    var x
        set(value) {
            glfwSetWindowPos(id, value, y)
            logger.info("Window X coordinate set successful. X: $value")
        }
        get() = windowData.x
    var y
        set(value) {
            glfwSetWindowPos(id, x, value)
            logger.info("Window X coordinate set successful. Y: $value")
        }
        get() = windowData.y
    var width
        set(value) {
            glfwSetWindowSize(id, value, height)
            logger.info("Window width set successful. Width: $value")
        }
        get() = windowData.width
    var height
        set(value) {
            glfwSetWindowSize(id, width, value)
            logger.info("Window height set successful. Height: $value")
        }
        get() = windowData.height

    val fps
        get() = windowData.fps
    val shouldClose
        get() = glfwWindowShouldClose(id)
    var cursor: Cursor? = null
        set(value) {
            if (value != null) {
                if (value.id != null) {
                    glfwSetCursor(id, value.id!!)
                    field = value
                    logger.info("Cursor set successful. Id: ${value.id}, Name: ${value.name}")
                } else {
                    logger.error("Unknown cursor. Name: ${value.name}")
                }
            } else {
                logger.error("Unable to set cursor. New Cursor: null")
            }
        }

    fun create(
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        title: String,
        monitor: Long,
        share: Long,
    ): Boolean {
        if (!glfwInit()) {
            logger.error("Unable to initialize GLFW")
            return false
        } else {
            logger.info("GLFW initialize successful")
        }

        windowData.id = glfwCreateWindow(width, height, title, monitor, share)
        if (id == NULL) {
            logger.error("Failed to create the window")
            return false
        }
        glfwMakeContextCurrent(id)
        setPositionCallback()
        setSizeCallback()
        windowData.x = x
        windowData.y = y
        windowData.width = width
        windowData.height = height
        logger.info("Window create successful. Width: $width, Height: $height, Title: $title, Monitor: $monitor, Share: $share")
        return true
    }

    fun cleanup(destroyWindow: Boolean) {
        if (destroyWindow) {
            glfwFreeCallbacks(id)
            glfwDestroyWindow(id)
            logger.info("Window destroy successful")
        }
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
        logger.info("GLFW terminate successful")
    }

    fun setErrorCallback(callback: (error: Int, description: String) -> Unit) {
        glfwSetErrorCallback { error, description -> callback(error, memUTF8(description)) }
        logger.info("Error callback set successful")
    }

    fun setCursorPosCallback(callback: (x: Double, y: Double) -> Unit) {
        glfwSetCursorPosCallback(id) { _, x, y -> callback(x, y) }
        logger.info("Cursor position callback set successful")
    }

    fun setKeyCallback(callback: (key: Int, scancode: Int, action: Int, mods: Int) -> Unit) {
        glfwSetKeyCallback(id) { _, key, scancode, action, mods -> callback(key, scancode, action, mods) }
        logger.info("Key callback set successful")
    }

    private fun setPositionCallback() {
        glfwSetWindowPosCallback(id) { _, x, y ->
            windowData.x = x
            windowData.y = y
        }
        logger.info("Window position callback set successful")
    }

    private fun setSizeCallback() {
        glfwSetWindowSizeCallback(id) { _, width, height ->
            windowData.width = width
            windowData.height = height
            content.setSize(width, height)
        }
        logger.info("Window size callback set successful")
    }

    fun setDefaultHints() = glfwDefaultWindowHints()

    fun setIcon(name: String) {
        val w = memAllocInt(1)
        val h = memAllocInt(1)
        val comp = memAllocInt(1)

        logger.info("Icon creation started. Name: $name")

        val icon16 = ioResourceToByteBuffer(name, 2048)
        val icon32 = ioResourceToByteBuffer(name, 8192)
        malloc(2).use { buffer ->
            if (icon16 == null) {
                logger.error("Unable get image buffer 16")
                return
            }
            logger.info("Image buffer 16 get successful")
            if (icon32 == null) {
                logger.error("Unable get image buffer 32")
                return
            }
            logger.info("Image buffer 32 get successful")

            val pixels16 = stbi_load_from_memory(icon16, w, h, comp, 4)
            if (pixels16 == null) {
                logger.error("Unable get pixels 16")
                return
            }
            logger.info("Pixels 16 get successful")
            buffer
                .position(0)
                .width(w[0])
                .height(h[0])
                .pixels(pixels16)

            val pixels32 = stbi_load_from_memory(icon32, w, h, comp, 4)
            if (pixels32 == null) {
                logger.error("Unable get pixels 32")
                return
            }
            logger.info("Pixels 32 get successful")
            buffer
                .position(1)
                .width(w[0])
                .height(h[0])
                .pixels(pixels32)

            glfwSetWindowIcon(id, buffer)
            stbi_image_free(pixels16)
            stbi_image_free(pixels32)
            logger.info("Icon set successful. Name: $name")
        }

        memFree(comp)
        memFree(h)
        memFree(w)
    }

    fun createCursor(vararg cursors: Cursor) {
        val w = memAllocInt(1)
        val h = memAllocInt(1)
        val comp = memAllocInt(1)

        cursors.forEach { obj ->
            logger.info("Cursor creation started. Name: ${obj.name}")

            val cursor = ioResourceToByteBuffer(obj.name, 8192)
            malloc().use { image ->
                if (cursor == null) {
                    logger.error("Unable get image buffer")
                    return
                }
                logger.info("Image buffer get successful")
                val pixels = stbi_load_from_memory(cursor, w, h, comp, 4)
                if (pixels == null) {
                    logger.error("Unable get pixels")
                    return
                }
                logger.info("Pixels get successful")
                image
                    .width(w[0])
                    .height(h[0])
                    .pixels(pixels)
                obj.id = glfwCreateCursor(image, 0, 0)
                stbi_image_free(pixels)
                logger.info("Cursor create successful. Id: ${obj.id}, Name: ${obj.name}")
            }
        }

        memFree(comp)
        memFree(h)
        memFree(w)
    }

    fun setNormalCursor() {
        glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_NORMAL)
        logger.info("Normal cursor set successful")
    }

    fun setDisabledCursor() {
        glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_DISABLED)
        logger.info("Disabled cursor set successful")
    }

    fun setCursorPosition(x: Int, y: Int) {
        glfwSetCursorPos(id, x.toDouble(), y.toDouble())
        logger.info("Cursor position set successful. X: $x, Y: $y")
    }

    fun postRender() {
        glfwSwapBuffers(id)
        glfwPollEvents()
    }

    private fun ioResourceToByteBuffer(name: String, bufferSize: Int): ByteBuffer? {
        var buffer: ByteBuffer

        val path = Paths.get(name)
        if (isReadable(path)) {
            newByteChannel(path).use { fc ->
                buffer = createByteBuffer(fc.size().toInt() + 1)
            }
        } else {
            currentThread().contextClassLoader.getResourceAsStream(name).use { source ->
                if (source != null) {
                    newChannel(source).use { rbc ->
                        buffer = createByteBuffer(bufferSize)
                        while (true) {
                            val bytes: Int = rbc.read(buffer)
                            if (bytes == -1) break
                            if (buffer.remaining() == 0) buffer = resizeBuffer(buffer, buffer.capacity() * 2)
                        }
                    }
                } else {
                    logger.error("Unknown name: $name")
                    return null
                }
            }
        }

        buffer.flip()
        return buffer
    }

    private fun resizeBuffer(buffer: ByteBuffer, newCapacity: Int): ByteBuffer {
        val newBuffer = createByteBuffer(newCapacity)
        buffer.flip()
        newBuffer.put(buffer)
        return newBuffer
    }
}
