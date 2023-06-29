package tdld4k.world

import tdld4k.math.Vector2

interface Tile {
    fun intersection(point: Vector2): Boolean
}
