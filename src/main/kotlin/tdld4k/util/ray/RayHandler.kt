package tdld4k.util.ray

import tdld4k.util.geometry.PointD
import tdld4k.util.geometry.PointI
import tdld4k.world.Tile
import tdld4k.world.World
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

fun rayCasting(world: World, start: PointD, maxDistance: Double, stepLength: Double, angle: Double): Ray {
    for (length in 0.0..maxDistance step 1 / stepLength) {
        val ray = ray(world, start, length, angle)
        if (!ray.tile.isOutOfWorldTile) {
            if (ray.rectangleIndex >= 0) {
                return ray
            }
        } else {
            return ray
        }
    }
    return Ray(Tile())
}

fun ray(world: World, start: PointD, length: Double, angle: Double): Ray {
    val tileSize = world.tileSize
    val edgeX = world.edgeX
    val edgeY = world.edgeY

    val xRayPoint = start.x + length * cos(angle)
    val yRayPoint = start.y + length * sin(angle)

    val xDistanceToStart = xRayPoint % tileSize
    val yDistanceToStart = yRayPoint % tileSize

    val xMap = ((xRayPoint - xDistanceToStart) / tileSize).roundToInt()
    val yMap = ((yRayPoint - yDistanceToStart) / tileSize).roundToInt()

    if (xRayPoint < 0 || yRayPoint < 0 || xMap > edgeX || yMap > edgeY) {
        return Ray(
            Tile(null, false),
            0,
            PointD(xRayPoint, yRayPoint),
            PointD(xDistanceToStart, yDistanceToStart),
            PointI(xMap, yMap),
            length,
            angle,
        )
    }

    val tile = world[xMap, yMap]
    val rayPointD = PointD(xRayPoint, yRayPoint)
    val tilePointD = PointD(xDistanceToStart, yDistanceToStart)
    val mapPointI = PointI(xMap, yMap)
    return Ray(
        tile,
        tile.tileShape?.intersection(tilePointD) ?: -1,
        rayPointD,
        tilePointD,
        mapPointI,
        length,
        angle,
    )
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
