package tdld4k.world

import tdld4k.math.Vector2Double
import java.awt.Paint

class FullTile(override val paint: Paint, tileSize: Double) : TileShape {
    override val leftTop = Vector2Double(0.0, 0.0)
    override val rightBot = Vector2Double(tileSize, tileSize)

    override fun intersection(point: Vector2Double): Boolean {
        return true
    }
}
