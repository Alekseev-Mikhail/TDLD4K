package tdld4k.world

import tdld4k.math.Vector
import tdld4k.qualityMustBeLess
import java.awt.Paint

abstract class TileShape {
    abstract val leftTop: Vector
    abstract val rightBot: Vector
    abstract val paint: Paint

    abstract fun intersection(point: Vector): Boolean

    open fun findError(tileSize: Double, quality: Double) {
        if (quality > (rightBot.x - leftTop.x) / 2 || quality > (rightBot.y - leftTop.y) / 2) {
//            qualityMustBeLess()
        }
    }
}
