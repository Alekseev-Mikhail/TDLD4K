package tdld4k.math

import tdld4k.world.TileShape

data class RayCastingOutput(val wallDistance: Double, val tileShape: TileShape?, val xMap: Int, val zMap: Int)
