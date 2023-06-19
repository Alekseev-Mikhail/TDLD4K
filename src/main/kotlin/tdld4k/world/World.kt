package tdld4k.world

class World(
    map: String,
    val mapWidth: Int,
    val tileSize: Int,
    private val airCode: Char,
    private val errorTile: TileShape,
    private val tileTypes: Map<Char, TileShape>,
) {
    val map = map.map { fromCode(it) }.chunked(mapWidth)

    operator fun get(x: Int, y: Int): TileShape? = map[y][x]

    private fun fromCode(code: Char): TileShape? {
        if (tileTypes.containsKey(code)) {
            return tileTypes[code]
        }
        if (code == airCode) {
            return null
        }
        return errorTile
    }
}
