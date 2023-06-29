package tdld4k.world

import tdld4k.math.Vector2
import java.awt.Paint

class AABBTile(
    override val paint: Paint,
    override val leftTop: Vector2,
    override val rightBot: Vector2,
) : TileShape {
    override fun intersection(point: Vector2): Boolean {
        return point.y in leftTop.y..rightBot.y && point.x in leftTop.x..rightBot.x
    }
}
