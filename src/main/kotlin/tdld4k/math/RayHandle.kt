package tdld4k.math

import tdld4k.player.Player
import tdld4k.world.AIR_TILE
import tdld4k.world.World
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class RayHandle(private val world: World, private val player: Player) {
    fun rayCasting(angle: Double): Ray {
        for (c in 0.0..player.renderDistance step 1 / player.quality) {
            val ray = ray(c, angle)
            if (!ray.tile.isAir && ray.tile.tileShape!!.intersection(ray.vector)) {
                return ray
            }
        }
        return Ray(AIR_TILE)
    }

    fun ray(distance: Double, angle: Double): Ray {
        val tileSize = world.tileSize
        val edgeX = world.edgeX
        val edgeY = world.edgeY

        val xRayPoint = player.x + distance * cos(angle)
        val yRayPoint = player.z + distance * sin(angle)

        val xDistanceToStart = xRayPoint % tileSize
        val yDistanceToStart = yRayPoint % tileSize

        val xMap = ((xRayPoint - xDistanceToStart) / tileSize).roundToInt()
        val yMap = ((yRayPoint - yDistanceToStart) / tileSize).roundToInt()

        if (xRayPoint < 0 || yRayPoint < 0 || xMap > edgeX || yMap > edgeY) {
            return Ray(
                world.outOfWorldTile,
                Vector(xRayPoint, yRayPoint),
                xMap,
                yMap,
                xDistanceToStart,
                yDistanceToStart,
                distance,
                angle,
            )
        }

        val tile = world[xMap, yMap]
        return Ray(
            tile,
            Vector(xRayPoint, yRayPoint),
            xMap,
            yMap,
            xDistanceToStart,
            yDistanceToStart,
            distance,
            angle,
        )
    }
}

infix fun ClosedRange<Double>.step(step: Double): Iterable<Double> {
    require(start.isFinite())
    require(endInclusive.isFinite())
    require(step > 0.0) { "Step must be positive, was: $step." }
    val sequence = generateSequence(start) { previous ->
        if (previous == Double.POSITIVE_INFINITY) return@generateSequence null
        val next = previous + step
        if (next > endInclusive) null else next
    }
    return sequence.asIterable()
}
