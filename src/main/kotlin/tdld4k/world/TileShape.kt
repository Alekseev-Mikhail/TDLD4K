package tdld4k.world

import tdld4k.math.Vector2Double
import java.awt.Paint

interface TileShape : Tile {
    val leftTop: Vector2Double
    val rightBot: Vector2Double
    val paint: Paint
}
