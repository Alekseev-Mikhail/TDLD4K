package tdld4k.world

import tdld4k.math.Vector2Double
import java.awt.Paint

class AABBTile(
    override val paint: Paint,
    private val leftTop: Vector2Double,
    private val rightBot: Vector2Double,
) : TileShape {
    override fun intersection(point: Vector2Double): Boolean {
        return point.y in leftTop.y..rightBot.y && point.x in leftTop.x..rightBot.x
    }
}
