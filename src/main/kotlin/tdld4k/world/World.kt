package tdld4k.world

class World(
    map: String,
    val mapWidth: Int,
    val tileSize: Double,
    private val errorTile: Tile,
    private val tileTypes: Map<Char, Tile>,
) {
    val map = map.map { fromCode(it) }.chunked(mapWidth)

    operator fun get(x: Int, y: Int): Tile = map[y][x]

    private fun fromCode(code: Char): Tile = tileTypes[code] ?: errorTile
}
