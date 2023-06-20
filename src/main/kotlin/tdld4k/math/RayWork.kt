package tdld4k.math

import tdld4k.player.Player
import tdld4k.world.World
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class GameRayWork(private val world: World, private val player: Player) {
    fun rayCasting(angle: Double): RayCastingOutput {
        for (c in 0.0..player.renderDistance step player.quality) {
            val point = pointOfRay(c, angle)
            if (point.isWall) {
                return RayCastingOutput(c, point.tileShape)
            }
        }
        return RayCastingOutput(0.0, null)
    }

    fun pointOfRay(distance: Double, angle: Double): PointOfRayOutput {
        val tileSize = world.tileSize

        val xPointOfRay = player.x + distance * cos(angle)
        val zPointOfRay = player.z + distance * sin(angle)

        val xDistanceToStart = xPointOfRay % tileSize
        val zDistanceToStart = zPointOfRay % tileSize

        val xMap = ((xPointOfRay - xDistanceToStart) / tileSize).roundToInt()
        val zMap = ((zPointOfRay - zDistanceToStart) / tileSize).roundToInt()

        val edgeX = world.mapWidth - 1
        val edgeY = world.map.size - 1
        if (xMap < 0 || zMap < 0 || xMap > edgeX || zMap > edgeY) {
            return PointOfRayOutput(Vector2Double(xPointOfRay, zPointOfRay), null, true)
        }

        val tileShape = world[xMap, zMap]
        if (tileShape != null) {
            val isWall = tileShape.intersection(Vector2Double(xDistanceToStart, zDistanceToStart))
            return PointOfRayOutput(Vector2Double(xPointOfRay, zPointOfRay), tileShape, isWall)
        }
        return PointOfRayOutput(Vector2Double(xPointOfRay, zPointOfRay), null, false)
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
