package tdld4k.world

import java.awt.Color

val AIR_TILE = Tile(null)
val ERROR_TILE = fromFullTile(Color(207, 3, 252))

data class Tile(val tileShape: TileShape?, val isAir: Boolean) {
    constructor(tileShape: TileShape?) : this(tileShape, tileShape == null)
}
