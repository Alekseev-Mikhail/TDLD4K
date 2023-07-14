package tdld4k.player.camera

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_COMPAT_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE
import org.lwjgl.glfw.GLFW.glfwCreateWindow
import org.lwjgl.glfw.GLFW.glfwDefaultWindowHints
import org.lwjgl.glfw.GLFW.glfwDestroyWindow
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwSetErrorCallback
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.glfw.GLFW.glfwSwapInterval
import org.lwjgl.glfw.GLFW.glfwTerminate
import org.lwjgl.glfw.GLFW.glfwWindowHint
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL46.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL46.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL46.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL46.GL_TRIANGLES
import org.lwjgl.opengl.GL46.GL_VERTEX_SHADER
import org.lwjgl.opengl.GL46.glBindVertexArray
import org.lwjgl.opengl.GL46.glClear
import org.lwjgl.opengl.GL46.glClearColor
import org.lwjgl.opengl.GL46.glDrawArrays
import org.lwjgl.opengl.GL46.glViewport
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.system.MemoryUtil.memUTF8
import tdld4k.ClientLife
import tdld4k.Mesh
import tdld4k.ShaderModuleData
import tdld4k.ShaderProgram
import tdld4k.Window
import tdld4k.math.RayHandle
import tdld4k.player.Player
import tdld4k.player.TranslatedToRadians
import tdld4k.world.World
import java.util.Timer
import kotlin.system.exitProcess

class Camera(
    private val world: World,
    private val player: Player,

    private val clientLife: ClientLife,
) {
    private val rayHandle = RayHandle(world, player)
    private var translatedPlayer = TranslatedToRadians(0.0, 0.0)
    private var isDebugVision = false
    private var fps = 0
    val fpsCounter = Timer("fps counter", false)

    private val window = Window(NULL, 0, 0)
    private var shaderProgram: ShaderProgram? = null
    private var mesh: Mesh? = null

    fun born(width: Int, height: Int, title: String, monitor: Long, share: Long) {
        glfwSetErrorCallback { error, description -> errorCallback(error, memUTF8(description)) }

        if (!glfwInit()) {
            die(false, "Unable to initialize GLFW")
        }

        window.windowId = glfwCreateWindow(width, height, title, monitor, share)
        window.width = width
        window.height = height
        if (window.windowId == NULL) {
            die(false, "Failed to create the GLFW window")
        }

        glfwDefaultWindowHints()
        glfwMakeContextCurrent(window.windowId)
        glfwSwapInterval(1)

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        clientLife.born(window)

        // TODO Logging successful go to live
        live()
    }

    private fun live() {
        createCapabilities()

        shaderProgram = ShaderProgram(
            listOf(
                ShaderModuleData("scene.vert", GL_VERTEX_SHADER),
                ShaderModuleData("scene.frag", GL_FRAGMENT_SHADER),
            ),
        )
        mesh = Mesh(
            floatArrayOf(
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
            ),
            3,
        )

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f)

        while (!glfwWindowShouldClose(window.windowId)) {
            // TODO Render
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            glViewport(0, 0, window.width, window.height)

            shaderProgram!!.bind()

            glBindVertexArray(mesh!!.vaoId)
            glDrawArrays(GL_TRIANGLES, 0, mesh!!.numVertices)
            glBindVertexArray(0)

            shaderProgram!!.unbind()

            glfwSwapBuffers(window.windowId)
            glfwPollEvents()
            clientLife.live(window)
        }

        // TODO Logging successful go to die
        die(true)
    }

    private fun die(isNatural: Boolean, description: String = "") {
        clientLife.die(window)
        Callbacks.glfwFreeCallbacks(window.windowId)
        glfwDestroyWindow(window.windowId)
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
        // TODO Logging success if isNatural is true and logging bad new if isNatural is false
        exitProcess(0)
    }

    private fun errorCallback(error: Int, description: String) {
        // TODO Logging error
    }

//    fun paintComponent(g: Graphics) {
//        val g2d = g as Graphics2D
//        val prevPaint = g2d.paint
//
//        g2d.setRenderingHint(
//            KEY_ANTIALIASING,
//            VALUE_ANTIALIAS_ON,
//        )
//        cameraLayers.bottom(g2d)
//        drawWalls(g2d)
//        cameraLayers.top(g2d)
//        fps++
//
//        g2d.paint = prevPaint
//    }
//
//    private fun drawWalls(g2d: Graphics2D) {
//        translatedPlayer = player.translateToRadians()
//        val polygons = mutableListOf<Polygon>()
//        val tiles = mutableListOf<Tile>()
//        val quality = 1 / player.quality
//
//        var lastRay = fromRayCasting(0)
//        var lastSide = if (!lastRay.tile.isAir) getSide(lastRay, quality) else NONE
//        if (!lastRay.tile.isAir) {
//            tiles.add(lastRay.tile)
//            polygons.addPolygon(lastRay.distance, lastRay.angle, -1)
//        }
//
//        for (x in 1 until width) {
//            val ray = fromRayCasting(x)
//            val currentSide: WallSide
//
//            if (!ray.tile.isAir) {
//                currentSide = getSide(ray, quality)
//                if (lastSide != NONE) {
//                    if (ray.tile != lastRay.tile || lastSide != currentSide || lastRay.rectangleIndex != ray.rectangleIndex) {
//                        tiles.add(ray.tile)
//                        polygons.updateAndAdd(lastRay.distance, ray.distance, lastRay.angle, ray.angle, x)
//                    }
//                } else {
//                    tiles.add(ray.tile)
//                    polygons.addPolygon(ray.distance, ray.angle, x)
//                }
//            } else {
//                currentSide = NONE
//                if (lastSide != NONE) {
//                    polygons.updatePolygon(lastRay.distance, lastRay.angle, x)
//                }
//            }
//            lastRay = ray
//            lastSide = currentSide
//        }
//
//        if (lastSide != NONE || lastRay.tile.isOutOfWorldTile) {
//            polygons.updatePolygon(lastRay.distance, lastRay.angle, width)
//        }
//
//        if (!isDebugVision) {
//            polygons.forEachIndexed { i, e ->
//                g2d.paint = tiles[i].tileShape?.paint ?: world.outOfWorldPaint
//                g2d.fillPolygon(e)
//            }
//        } else {
//            polygons.forEach { e ->
//                g2d.paint = BLACK
//                g2d.drawPolygon(e)
//            }
//        }
//    }
//
//    private fun getSide(ray: Ray, quality: Double): WallSide {
//        if (!ray.tile.isOutOfWorldTile) {
//            val xDistance = ray.tilePoint.x
//            val yDistance = ray.tilePoint.y
//            val tileShape = ray.tile.tileShape!!
//            val leftTopX = tileShape.rectangles[ray.rectangleIndex].leftTop.x
//            val leftTopY = tileShape.rectangles[ray.rectangleIndex].leftTop.y
//            val rightBotX = tileShape.rectangles[ray.rectangleIndex].rightBot.x
//            val rightBotY = tileShape.rectangles[ray.rectangleIndex].rightBot.y
//            return when {
//                yDistance in leftTopY..leftTopY + quality -> FORWARD
//                yDistance in rightBotY - quality..rightBotY -> BACK
//                xDistance in leftTopX..leftTopX + quality -> LEFT
//                xDistance in rightBotX - quality..rightBotX -> RIGHT
//                else -> NONE
//            }
//        } else {
//            val xDistance = abs(ray.tilePoint.x)
//            val yDistance = abs(ray.tilePoint.y)
//            val tileSize = world.tileSize
//            return when {
//                yDistance in 0.0..quality -> FORWARD
//                yDistance in tileSize - quality..tileSize -> BACK
//                xDistance in 0.0..quality -> LEFT
//                xDistance in tileSize - quality..tileSize -> RIGHT
//                else -> NONE
//            }
//        }
//    }
//
//    private fun MutableList<Polygon>.updateAndAdd(
//        lastWallDistance: Double,
//        wallDistance: Double,
//        lastAngle: Double,
//        angle: Double,
//        x: Int,
//    ) {
//        updatePolygon(lastWallDistance, lastAngle, x)
//        addPolygon(wallDistance, angle, x)
//    }
//
//    private fun MutableList<Polygon>.updatePolygon(
//        wallDistance: Double,
//        angle: Double,
//        x: Int,
//    ) {
//        val lastPolygon = get(size - 1)
//        val columnHeight = height / (wallDistance * cos(angle - translatedPlayer.xDirection))
//        val rootOfColumn = height / 2 * player.yDirection
//        val topColumn = columnHeight * (1 - player.y)
//        val bottomColumn = columnHeight * player.y
//
//        lastPolygon.xpoints[1] = x
//        lastPolygon.xpoints[2] = x
//
//        lastPolygon.ypoints[1] = (rootOfColumn - topColumn).roundToInt()
//        lastPolygon.ypoints[2] = (rootOfColumn + bottomColumn).roundToInt()
//    }
//
//    private fun MutableList<Polygon>.addPolygon(
//        wallDistance: Double,
//        angle: Double,
//        x: Int,
//    ) {
//        val newPolygon = Polygon(IntArray(4), IntArray(4), 4)
//        val columnHeight = height / (wallDistance * cos(angle - translatedPlayer.xDirection))
//        val rootOfColumn = height / 2 * player.yDirection
//        val topColumn = columnHeight * (1 - player.y)
//        val bottomColumn = columnHeight * player.y
//
//        newPolygon.xpoints[0] = x
//        newPolygon.xpoints[3] = x
//
//        newPolygon.ypoints[0] = (rootOfColumn - topColumn).roundToInt()
//        newPolygon.ypoints[3] = (rootOfColumn + bottomColumn).roundToInt()
//
//        add(newPolygon)
//    }
//
//    private fun fromRayCasting(x: Int) =
//        rayHandle.rayCasting(translatedPlayer.xDirection - translatedPlayer.fov / 2 + translatedPlayer.fov * x / width)
//
//    fun addComponents() {
//        cameraLayers.addComponents(this)
//    }
//
//    fun setCameraLayers(cameraLayers: CameraLayers) {
//        this.cameraLayers = cameraLayers
//    }
//
//    fun enableDebugVision() {
//        isDebugVision = true
//    }
//
//    fun disableDebugVision() {
//        isDebugVision = false
//    }
}

private enum class WallSide {
    FORWARD,
    BACK,
    LEFT,
    RIGHT,
    NONE,
}
