package tdld4k.util.ray

import tdld4k.util.geometry.PointD
import tdld4k.util.geometry.PointI
import tdld4k.world.Tile

data class Ray(
    val tile: Tile,
    val rectangleIndex: Int = -1,
    val rayPointD: PointD = PointD(),
    val tilePointD: PointD = PointD(),
    val mapPoint: PointI = PointI(),
    val length: Double = 0.0,
    val angle: Double = 0.0,
)
