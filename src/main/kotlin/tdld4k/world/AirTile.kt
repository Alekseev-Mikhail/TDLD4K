package tdld4k.world

import tdld4k.math.Vector2Double

class AirTile : Tile {
    override fun intersection(point: Vector2Double): Boolean {
        return false
    }
}
