package tdld4k.world

import tdld4k.math.Vector2Double

interface Tile {
    fun intersection(point: Vector2Double): Boolean
}
