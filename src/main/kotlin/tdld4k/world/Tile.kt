package tdld4k.world

import java.awt.Color
import java.awt.Paint

val ERROR_PAINT: Paint = Color(207, 3, 252)

data class Tile(val tileShape: TileShape? = null, val isAir: Boolean = tileShape == null) {
    val isOutOfWorldTile = tileShape == null && !isAir
}
