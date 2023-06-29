package tdld4k.world

import tdld4k.math.Vector2

class AirTile : Tile {
    override fun intersection(point: Vector2): Boolean {
        return false
    }
}
