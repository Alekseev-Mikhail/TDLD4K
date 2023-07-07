package tdld4k.world

data class World(
    val map: List<List<Tile>>,
    val mapWidth: Int,
    val tileSize: Double,
    val airCode: Char,
    val errorTile: Tile,
    val tileTypes: Map<Char, Tile>,
) {
    constructor(
        map: String,
        mapWidth: Int,
        tileSize: Double,
        quality: Double,
        tileTypes: Map<Char, Tile>,
        airCode: Char,
        errorTile: Tile? = null,
    ) : this(
        map.map { fromTileTypes(tileSize, 1 / quality, tileTypes, airCode, errorTile, it) }.chunked(mapWidth),
        mapWidth,
        tileSize,
        airCode,
        getErrorTile(errorTile, tileSize),
        tileTypes,
    )

    var outOfWorldTile = errorTile
    val edgeX = mapWidth - 1
    val edgeY = map.size - 1

    operator fun get(x: Int, y: Int): Tile = map[y][x]
}

private fun getErrorTile(tile: Tile?, tileSize: Double) =
    if (tile?.tileShape != null) {
        if (tile.tileShape is FullTile) {
            tile.tileShape.setTileSize(tileSize)
        }
        tile
    } else {
        val errorTile = ERROR_TILE
        if (errorTile.tileShape is FullTile) {
            errorTile.tileShape.setTileSize(tileSize)
        }
        errorTile
    }

private fun fromTileTypes(
    tileSize: Double,
    quality: Double,
    tileTypes: Map<Char, Tile>,
    airCode: Char,
    errorTile: Tile?,
    code: Char,
): Tile {
    if (code == airCode) return AIR_TILE
    val tile = tileTypes[code]
    if (tile != null) {
        if (tile.tileShape != null) {
            tile.tileShape.findError(tileSize, quality)
        }
    }
    return tile ?: getErrorTile(errorTile, tileSize)
}
