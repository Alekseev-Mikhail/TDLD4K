package tdld4k.world

import tdld4k.math.Rectangle
import tdld4k.math.Vector
import java.awt.Paint

class AABBTile(
    override val paint: Paint,
    override val rectangles: List<Rectangle>,
) : TileShape {
    constructor(paint: Paint, leftTop: Vector, rightBot: Vector) : this(paint, listOf(Rectangle(leftTop, rightBot)))

    override fun intersection(point: Vector): Int {
        rectangles.forEachIndexed { i, e ->
            if (point.y in e.leftTop.y..e.rightBot.y && point.x in e.leftTop.x..e.rightBot.x) {
                return i
            }
        }
        return -1
    }
}

fun fromAABBTile(paint: Paint, rectangles: List<Rectangle>) = Tile(AABBTile(paint, rectangles))
