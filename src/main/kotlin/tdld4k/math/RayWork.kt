package tdld4k.math

import tdld4k.player.Player
import tdld4k.world.AirTile
import tdld4k.world.World
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class RayWork(private val world: World, private val player: Player) {
    fun rayCasting(angle: Double): RayCastingOutput {
        for (c in 0.0..player.renderDistance step 1 / player.quality) {
            val point = pointOfRay(c, angle)
            if (point.isWall) {
                return RayCastingOutput(
                    c,
                    point.tile,
                    point.xMap,
                    point.yMap,
                    point.xDistanceToStart,
                    point.yDistanceToStart,
                )
            }
        }
        return RayCastingOutput(0.0, AirTile(), 0, 0, 0.0, 0.0)
    }

    fun pointOfRay(distance: Double, angle: Double): PointOfRayOutput {
        val tileSize = world.tileSize

        val xPointOfRay = player.x + distance * cos(angle)
        val yPointOfRay = player.z + distance * sin(angle)

        val xDistanceToStart = xPointOfRay % tileSize
        val yDistanceToStart = yPointOfRay % tileSize

        val xMap = ((xPointOfRay - xDistanceToStart) / tileSize).roundToInt()
        val yMap = ((yPointOfRay - yDistanceToStart) / tileSize).roundToInt()

        val edgeX = world.mapWidth - 1
        val edgeY = world.map.size - 1
        if (xMap < 0 || yMap < 0 || xMap > edgeX || yMap > edgeY) {
            return PointOfRayOutput(
                Vector2(xPointOfRay, yPointOfRay),
                AirTile(),
                true,
                xMap,
                yMap,
                xDistanceToStart,
                yDistanceToStart,
            )
        }

        val tile = world[xMap, yMap]
        val isWall = tile.intersection(Vector2(xDistanceToStart, yDistanceToStart))
        return PointOfRayOutput(
            Vector2(xPointOfRay, yPointOfRay),
            tile,
            isWall,
            xMap,
            yMap,
            xDistanceToStart,
            yDistanceToStart,
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
