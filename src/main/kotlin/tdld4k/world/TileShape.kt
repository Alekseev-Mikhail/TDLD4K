package tdld4k.world

import tdld4k.math.Rectangle
import tdld4k.math.Vector
import java.awt.Paint

interface TileShape {
    val rectangles: List<Rectangle>
    val paint: Paint

    fun intersection(point: Vector): Int
}
