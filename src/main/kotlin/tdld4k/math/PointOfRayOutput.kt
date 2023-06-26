package tdld4k.math

import tdld4k.world.TileShape

data class PointOfRayOutput(
    val vector: Vector2Double,
    val tileShape: TileShape?,
    val isWall: Boolean,
    val xMap: Int,
    val zMap: Int,
)
