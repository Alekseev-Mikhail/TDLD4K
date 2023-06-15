package tdld4k.world

import java.awt.Paint

class World(
    map: String,
    mapWidth: Int,
    private val airCode: Char,
    private val errorColor: Paint,
    private val tileTypes: Map<Char, TileShape>,
    val tileSize: Int,
) {
    private val map = map.map { fromCode(it) }.chunked(mapWidth)

    operator fun get(x: Int, y: Int): TileShape? = map[y][x]

    private fun fromCode(code: Char): TileShape? {
        if (tileTypes.containsKey(code)) {
            return tileTypes[code]
        }
        if (code == airCode) {
            return null
        }
        return FullTile(errorColor)
    }
}
