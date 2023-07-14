package tdld4k.player.camera

import org.lwjgl.glfw.Callbacks
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
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL46.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL46.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL46.glClear
import org.lwjgl.opengl.GL46.glClearColor
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.system.MemoryUtil.memUTF8
import org.slf4j.Logger
import tdld4k.ClientLife
import tdld4k.Window
import tdld4k.math.RayHandle
import tdld4k.player.Player
import tdld4k.player.TranslatedToRadians
import tdld4k.world.World
import java.util.Timer
import kotlin.system.exitProcess

class Camera(
    world: World,
    player: Player,
    private val clientLife: ClientLife,
    private val logger: Logger,
) {
    private val rayHandle = RayHandle(world, player)
    private var translatedPlayer = TranslatedToRadians(0.0, 0.0)
    private var isDebugVision = false
    private var fps = 0
    val fpsCounter = Timer("fps counter", false)

    private val window = Window(NULL, 0, 0)

    constructor(
        world: World,
        player: Player,
        clientLife: ClientLife,
        logger: Logger,
        width: Int = 500,
        height: Int = 500,
        title: String = "TDLD4K",
        monitor: Long = NULL,
        share: Long = NULL,
    ) : this(world, player, clientLife, logger) {
        createWindow(width, height, title, monitor, share)
    }

    private fun createWindow(width: Int, height: Int, title: String, monitor: Long, share: Long) {
        glfwSetErrorCallback { error, description -> errorCallback(error, memUTF8(description)) }

        if (!glfwInit()) {
            cleanup(false, "Unable to initialize GLFW")
        }

        window.windowId = glfwCreateWindow(width, height, title, monitor, share)
        window.width = width
        window.height = height
        if (window.windowId == NULL) {
            cleanup(false, "Failed to create the GLFW window")
        }

        glfwDefaultWindowHints()
        glfwMakeContextCurrent(window.windowId)
        glfwSwapInterval(1)

        clientLife.born(window)

        logger.info("Window created successfully")
    }

    private fun startRenderLoop() {
        createCapabilities()

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f)

        while (!glfwWindowShouldClose(window.windowId)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            // TODO Render

            glfwSwapBuffers(window.windowId)
            glfwPollEvents()
            clientLife.live(window)
        }

        logger.info("Render loop stopped")
        cleanup()
    }

    fun cleanup(errorDescription: String = "") {
        clientLife.die(window)
        Callbacks.glfwFreeCallbacks(window.windowId)
        glfwDestroyWindow(window.windowId)
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
        if (errorDescription.isBlank()) {
            logger.info("Application stopped successfully without problems")
        } else {
            logger.error("Application stopped with problem: $errorDescription")
        }
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
