package tdld4k.world

import tdld4k.math.Vector
import java.awt.Paint

interface TileShape {
    val leftTop: Vector
    val rightBot: Vector
    val paint: Paint

    fun intersection(point: Vector): Boolean
}
