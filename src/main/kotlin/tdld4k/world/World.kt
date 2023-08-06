package tdld4k.world

import tdld4k.util.Paint

private var errorPaint = ERROR_PAINT

data class World(
    val map: List<List<Tile>>,
    val mapWidth: Int,
    val tileSize: Double,
) {
    constructor(
        rawMap: String,
        mapWidth: Int,
        tileTypes: Map<Char, TileShape>,
        tileSize: Double,
        quality: Double,
        airCode: Char,
        errorPaint: Paint? = null,
    ) : this(
        getMap(rawMap, mapWidth, tileTypes, tileSize, quality, airCode, errorPaint),
        mapWidth,
        tileSize,
    )

    var outOfWorldPaint = errorPaint
    val edgeX = mapWidth - 1
    val edgeY = map.size - 1

    operator fun get(x: Int, y: Int): Tile = map[y][x]
}

private fun getMap(
    rawMap: String,
    mapWidth: Int,
    tileTypes: Map<Char, TileShape>,
    tileSize: Double,
    quality: Double,
    airCode: Char,
    errorPaint: Paint?,
): List<List<Tile>> {
    val translatedQuality = 1 / quality
    if (errorPaint != null) tdld4k.world.errorPaint = errorPaint
    return rawMap.map { fromTileTypes(translatedQuality, tileTypes, tileSize, airCode, it) }.chunked(mapWidth)
}

private fun fromTileTypes(
    quality: Double,
    tileTypes: Map<Char, TileShape>,
    tileSize: Double,
    airCode: Char,
    code: Char,
): Tile {
    if (code == airCode) return Tile()
    val exampleTile = tileTypes[code]
    var tile = fromFullTile(tileSize, errorPaint)
    if (exampleTile != null) {
        when (exampleTile) {
            is FullTile -> tile = fromFullTile(tileSize, exampleTile.paint)
            is AABBTile -> tile = fromAABBTile(exampleTile.paint, exampleTile.rectangles)
        }
    }
    return findError(tile, fromFullTile(tileSize, errorPaint), quality)
}

private fun findError(tile: Tile, errorTile: Tile, quality: Double): Tile {
    tile.tileShape!!.rectangles.forEach { rectangle ->
        val leftTop = rectangle.leftTop
        val rightBot = rectangle.rightBot
        if (quality > (rightBot.x - leftTop.x) / 2 || quality > (rightBot.y - leftTop.y) / 2) return errorTile
    }
    return tile
}
