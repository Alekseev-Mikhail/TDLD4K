package tdld4k.world

import tdld4k.util.Paint
import tdld4k.util.geometry.PointD
import tdld4k.util.geometry.Rectangle

class AABBTile(
    override val paint: Paint,
    override val rectangles: List<Rectangle>,
) : TileShape {
    constructor(paint: Paint, leftTop: PointD, rightBot: PointD) : this(paint, listOf(Rectangle(leftTop, rightBot)))

    override fun intersection(pointD: PointD): Int {
        rectangles.forEachIndexed { i, e ->
            if (pointD.y in e.leftTop.y..e.rightBot.y && pointD.x in e.leftTop.x..e.rightBot.x) {
                return i
            }
        }
        return -1
    }
}

fun fromAABBTile(paint: Paint, rectangles: List<Rectangle>) = Tile(AABBTile(paint, rectangles))
