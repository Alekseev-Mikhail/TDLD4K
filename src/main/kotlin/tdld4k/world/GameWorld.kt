package tdld4k.world

import java.awt.Paint

class GameWorld(
    private val airCode: Char,
    private val errorColor: Paint,
    private val tileTypes: Map<Char, GameShape>,
    val tileSize: Int,
    map: String,
    mapWidth: Int,
) {

    val map = map.map { fromCode(it) }.chunked(mapWidth)

    operator fun get(x: Int, y: Int): GameShape? = map[y][x]

    private fun fromCode(code: Char): GameShape? {
        if (tileTypes.containsKey(code)) {
            return tileTypes[code]
        }
        if (code == airCode) {
            return null
        }
        return GameFullTile(errorColor)
    }

}