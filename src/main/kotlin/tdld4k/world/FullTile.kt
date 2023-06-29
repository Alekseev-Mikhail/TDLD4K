package tdld4k.world

import tdld4k.math.Vector2
import java.awt.Paint

class FullTile(override val paint: Paint, tileSize: Double) : TileShape {
    override val leftTop = Vector2(0.0, 0.0)
    override val rightBot = Vector2(tileSize, tileSize)

    override fun intersection(point: Vector2): Boolean {
        return true
    }
}
