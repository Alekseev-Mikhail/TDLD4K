package tdld4k.math

import tdld4k.player.GamePlayer
import tdld4k.world.GameWorld
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class GameRayWork(private val world: GameWorld, private val player: GamePlayer) {
    fun rayCasting(angle: Double): GameRayCastingOutput {
        for (c in 0.0..player.renderDistance step player.quality) {
            val point = pointOfRay(c, angle)
            if (point.isWall) {
                return GameRayCastingOutput(c, point.shape)
            }
        }
        return GameRayCastingOutput(0.0, null)
    }

    fun pointOfRay(distance: Double, angle: Double): GamePointOfRayOutput {
        val tileSize = world.tileSize

        val xPointOfRay = player.x + distance * cos(angle)
        val yPointOfRay = player.y + distance * sin(angle)

        val xDistanceToStart = xPointOfRay % tileSize
        val yDistanceToStart = yPointOfRay % tileSize

        val xMap = ((xPointOfRay - xDistanceToStart) / tileSize).roundToInt()
        val yMap = ((yPointOfRay - yDistanceToStart) / tileSize).roundToInt()

        val shape = world[xMap, yMap]
        if (shape != null) {
            val isWall = shape.intersection(Vector2Double(xDistanceToStart, yDistanceToStart))
            return GamePointOfRayOutput(Vector2Double(xPointOfRay, yPointOfRay), shape, isWall)
        }
        return GamePointOfRayOutput(Vector2Double(xPointOfRay, yPointOfRay), null, false)
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