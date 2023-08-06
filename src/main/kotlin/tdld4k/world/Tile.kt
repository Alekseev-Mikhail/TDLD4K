package tdld4k.world

import tdld4k.util.Paint

val ERROR_PAINT = Paint(207, 3, 252)

data class Tile(val tileShape: TileShape? = null, val isAir: Boolean = tileShape == null) {
    val isOutOfWorldTile = tileShape == null && !isAir
}
