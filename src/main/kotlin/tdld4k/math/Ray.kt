package tdld4k.math

import tdld4k.world.Tile
import java.awt.Point

data class Ray(
    val tile: Tile,
    val rectangleIndex: Int = -1,
    val rayPoint: Vector = Vector(0.0, 0.0),
    val tilePoint: Vector = Vector(0.0, 0.0),
    val mapPoint: Point = Point(0, 0),
    val distance: Double = 0.0,
    val angle: Double = 0.0,
)
