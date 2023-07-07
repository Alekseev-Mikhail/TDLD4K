package tdld4k.world

import tdld4k.math.Vector
import java.awt.Paint

class AABBTile(
    override val paint: Paint,
    override val leftTop: Vector,
    override val rightBot: Vector,
) : TileShape() {
    override fun intersection(point: Vector): Boolean {
        return point.y in leftTop.y..rightBot.y && point.x in leftTop.x..rightBot.x
    }
}

fun fromAABBTile(paint: Paint, leftTop: Vector, rightBot: Vector) = Tile(AABBTile(paint, leftTop, rightBot))
