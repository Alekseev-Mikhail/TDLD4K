package tdld4k.world

import tdld4k.util.Paint
import tdld4k.util.geometry.PointD
import tdld4k.util.geometry.Rectangle

class FullTile(override val paint: Paint) : TileShape {
    override val rectangles = listOf(Rectangle(PointD(0.0, 0.0), PointD(0.0, 0.0)))

    override fun intersection(pointD: PointD): Int {
        return 0
    }

    fun setTileSize(value: Double) {
        rectangles[0].rightBot = PointD(value, value)
    }
}

fun fromFullTile(tileSize: Double, paint: Paint): Tile {
    val tileShape = FullTile(paint)
    tileShape.setTileSize(tileSize)
    return Tile(tileShape)
}
