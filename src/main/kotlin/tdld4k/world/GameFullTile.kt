package tdld4k.world

import tdld4k.math.Vector2Double
import java.awt.Paint

class GameFullTile(override val paint: Paint) : GameShape {
    override fun intersection(point: Vector2Double): Boolean {
        return true
    }
}
