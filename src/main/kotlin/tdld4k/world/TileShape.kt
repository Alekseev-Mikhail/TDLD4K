package tdld4k.world

import tdld4k.math.Vector2
import java.awt.Paint

interface TileShape : Tile {
    val leftTop: Vector2
    val rightBot: Vector2
    val paint: Paint
}
