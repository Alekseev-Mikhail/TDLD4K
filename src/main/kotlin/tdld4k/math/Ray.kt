package tdld4k.math

import tdld4k.world.Tile

data class Ray(
    val tile: Tile,
    val vector: Vector = Vector(0.0, 0.0),
    val xMap: Int = 0,
    val yMap: Int = 0,
    val xDistanceToStart: Double = 0.0,
    val yDistanceToStart: Double = 0.0,
    val distance: Double = 0.0,
    val angle: Double = 0.0,
)
