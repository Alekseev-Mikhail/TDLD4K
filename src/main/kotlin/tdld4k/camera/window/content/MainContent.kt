package tdld4k.camera.window.content

import tdld4k.camera.window.Window
import tdld4k.camera.window.content.WallSide.BACK
import tdld4k.camera.window.content.WallSide.FORWARD
import tdld4k.camera.window.content.WallSide.LEFT
import tdld4k.camera.window.content.WallSide.NONE
import tdld4k.camera.window.content.WallSide.RIGHT
import tdld4k.entity.Entity
import tdld4k.options.BasicOptions
import tdld4k.util.geometry.PointD
import tdld4k.util.geometry.Quad
import tdld4k.util.ray.Ray
import tdld4k.util.ray.rayCasting
import tdld4k.world.Tile
import tdld4k.world.World
import java.lang.Math.toRadians
import kotlin.math.abs
import kotlin.math.cos

open class MainContent(
    private val world: World,
    private val entity: Entity,
    private val options: BasicOptions,
) : Content() {
    override val isMain = true

    private lateinit var window: Window
    private val width
        get() = window.width
    private val height
        get() = window.height

    override fun changeOn(window: Window) {
        super.changeOn(window)
        window.setDisabledCursor()
    }

    override fun render(window: Window) {
        super.render(window)
        this.window = window
        drawWalls()
    }

    private fun drawWalls() {
        val direction = toRadians(entity.horizontalDirection)
        val fov = toRadians(options.fov)
        val quads = mutableListOf<Quad>()
        val tiles = mutableListOf<Tile>()
        val quality = 1 / options.quality

        var lastRay = fromRayCasting(0, direction, fov)
        var lastSide = if (!lastRay.tile.isAir) getSide(lastRay, quality) else NONE
        if (!lastRay.tile.isAir) {
            tiles.add(lastRay.tile)
            quads.addNew(direction, lastRay.length, lastRay.angle, -1)
        }

        for (x in 1 until width) {
            val ray = fromRayCasting(x, direction, fov)
            val currentSide: WallSide

            if (!ray.tile.isAir) {
                currentSide = getSide(ray, quality)
                if (lastSide != NONE) {
                    if (ray.tile != lastRay.tile || lastSide != currentSide || lastRay.rectangleIndex != ray.rectangleIndex) {
                        tiles.add(ray.tile)
                        quads.updateAndAdd(direction, lastRay.length, ray.length, lastRay.angle, ray.angle, x)
                    }
                } else {
                    tiles.add(ray.tile)
                    quads.addNew(direction, ray.length, ray.angle, x)
                }
            } else {
                currentSide = NONE
                if (lastSide != NONE) {
                    quads.updateLast(direction, lastRay.length, lastRay.angle, x)
                }
            }
            lastRay = ray
            lastSide = currentSide
        }

        if (lastSide != NONE || lastRay.tile.isOutOfWorldTile) {
            quads.updateLast(direction, lastRay.length, lastRay.angle, width)
        }

        quads.forEachIndexed { i, quad ->
            paint = tiles[i].tileShape?.paint ?: world.outOfWorldPaint
            drawQuad(quad)
        }
    }

    private fun getSide(ray: Ray, quality: Double): WallSide {
        if (!ray.tile.isOutOfWorldTile) {
            val xDistance = ray.tilePointD.x
            val yDistance = ray.tilePointD.y
            val tileShape = ray.tile.tileShape!!
            val leftTopX = tileShape.rectangles[ray.rectangleIndex].leftTop.x
            val leftTopY = tileShape.rectangles[ray.rectangleIndex].leftTop.y
            val rightBotX = tileShape.rectangles[ray.rectangleIndex].rightBot.x
            val rightBotY = tileShape.rectangles[ray.rectangleIndex].rightBot.y
            return when {
                yDistance in leftTopY..leftTopY + quality -> FORWARD
                yDistance in rightBotY - quality..rightBotY -> BACK
                xDistance in leftTopX..leftTopX + quality -> LEFT
                xDistance in rightBotX - quality..rightBotX -> RIGHT
                else -> NONE
            }
        } else {
            val xDistance = abs(ray.tilePointD.x)
            val yDistance = abs(ray.tilePointD.y)
            val tileSize = world.tileSize
            return when {
                yDistance in 0.0..quality -> FORWARD
                yDistance in tileSize - quality..tileSize -> BACK
                xDistance in 0.0..quality -> LEFT
                xDistance in tileSize - quality..tileSize -> RIGHT
                else -> NONE
            }
        }
    }

    private fun MutableList<Quad>.updateAndAdd(
        direction: Double,
        lastWallDistance: Double,
        wallDistance: Double,
        lastAngle: Double,
        angle: Double,
        x: Int,
    ) {
        updateLast(direction, lastWallDistance, lastAngle, x)
        addNew(direction, wallDistance, angle, x)
    }

    private fun MutableList<Quad>.updateLast(
        direction: Double,
        wallDistance: Double,
        angle: Double,
        x: Int,
    ) {
        val lastIndex = size - 1
        val last = get(lastIndex)
        val columnHeight = height / (wallDistance * cos(angle - direction))
        val rootOfColumn = height / 2 * entity.verticalDirection
        val topColumn = columnHeight * (1 - entity.z)
        val bottomColumn = columnHeight * entity.z

        val xAsDouble = x.toDouble()
        val newLast = Quad(
            last.first,
            PointD(xAsDouble, rootOfColumn - topColumn),
            PointD(xAsDouble, rootOfColumn + bottomColumn),
            last.fourth,
        )

        set(lastIndex, newLast)
    }

    private fun MutableList<Quad>.addNew(
        direction: Double,
        wallDistance: Double,
        angle: Double,
        x: Int,
    ) {
        val columnHeight = height / (wallDistance * cos(angle - direction))
        val root = height / 2 * entity.verticalDirection
        val top = columnHeight * (1 - entity.z)
        val bottom = columnHeight * entity.z

        val xAsDouble = x.toDouble()
        val new = Quad(
            PointD(xAsDouble, root - top),
            PointD(),
            PointD(),
            PointD(xAsDouble, root + bottom),
        )

        add(new)
    }

    private fun fromRayCasting(x: Int, direction: Double, fov: Double) =
        rayCasting(
            world,
            PointD(entity.x, entity.y),
            options.renderDistance,
            options.quality,
            direction - fov / 2 + fov * x / width,
        )
}

private enum class WallSide {
    FORWARD,
    BACK,
    LEFT,
    RIGHT,
    NONE,
}
