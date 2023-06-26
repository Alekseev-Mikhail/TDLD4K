package tdld4k.world

import tdld4k.math.Vector2Double
import java.awt.Paint

class FullTile(override val paint: Paint) : TileShape {
    override fun intersection(point: Vector2Double): Boolean {
        return true
    }
}
