package tdld4k.math

import tdld4k.world.Tile

data class RayCastingOutput(
    val wallDistance: Double,
    val tile: Tile,
    val xMap: Int,
    val yMap: Int,
    val xDistanceToStart: Double,
    val yDistanceToStart: Double,
)
