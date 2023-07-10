package tdld4k.world

import tdld4k.math.Vector
import java.awt.Paint

class FullTile(override val paint: Paint) : TileShape {
    override var leftTop = Vector(0.0, 0.0)
    override var rightBot = Vector(0.0, 0.0)

    override fun intersection(point: Vector): Boolean {
        return true
    }

    fun setTileSize(value: Double) {
        rightBot = Vector(value, value)
    }
}

fun fromFullTile(tileSize: Double, paint: Paint): Tile {
    val tileShape = FullTile(paint)
    tileShape.setTileSize(tileSize)
    return Tile(tileShape)
}
