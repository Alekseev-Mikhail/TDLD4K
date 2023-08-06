package tdld4k.world

import tdld4k.util.Paint
import tdld4k.util.geometry.PointD
import tdld4k.util.geometry.Rectangle

interface TileShape {
    val rectangles: List<Rectangle>
    val paint: Paint

    fun intersection(pointD: PointD): Int
}
