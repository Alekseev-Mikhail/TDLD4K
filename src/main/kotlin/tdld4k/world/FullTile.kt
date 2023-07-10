package tdld4k.world

import tdld4k.math.Rectangle
import tdld4k.math.Vector
import java.awt.Paint

class FullTile(override val paint: Paint) : TileShape {
    override val rectangles = listOf(Rectangle(Vector(0.0, 0.0), Vector(0.0, 0.0)))

    override fun intersection(point: Vector): Int {
        return 0
    }

    fun setTileSize(value: Double) {
        rectangles[0].rightBot = Vector(value, value)
    }
}

fun fromFullTile(tileSize: Double, paint: Paint): Tile {
    val tileShape = FullTile(paint)
    tileShape.setTileSize(tileSize)
    return Tile(tileShape)
}
