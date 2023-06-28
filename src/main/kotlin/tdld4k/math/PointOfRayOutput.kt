package tdld4k.math

import tdld4k.world.Tile

data class PointOfRayOutput(
    val vector: Vector2Double,
    val tile: Tile,
    val isWall: Boolean,
    val xMap: Int,
    val yMap: Int,
    val xDistanceToStart: Double,
    val yDistanceToStart: Double,
)
