package tdld4k.world

import tdld4k.math.Vector2Double
import java.awt.Paint

interface GameShape {
    val paint: Paint
    fun intersection(point: Vector2Double): Boolean
}
