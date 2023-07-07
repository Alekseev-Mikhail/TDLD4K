package tdld4k.world

import tdld4k.math.Vector
import java.awt.Paint

class FullTile(override val paint: Paint) : TileShape() {
    override var leftTop = Vector(0.0, 0.0)
    override var rightBot = Vector(0.0, 0.0)

    override fun intersection(point: Vector): Boolean {
        return true
    }

    override fun findError(tileSize: Double, quality: Double) {
        super.findError(tileSize, quality)
        setTileSize(tileSize)
    }

    fun setTileSize(value: Double) {
        leftTop = Vector(0.0, 0.0)
        rightBot = Vector(value, value)
    }
}

fun fromFullTile(paint: Paint) = Tile(FullTile(paint))
