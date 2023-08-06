package tdld4k.entity

import tdld4k.util.geometry.PointD

abstract class Entity {
    abstract var x: Double
    abstract var y: Double
    abstract var z: Double
    var horizontalDirection = 0.0
        set(value) {
            field = if (value < 0) {
                359.0
            } else if (value >= 360) {
                0.0
            } else {
                value
            }
        }
    var verticalDirection = 1.0

    fun translate(vector: PointD) {
        x = vector.x
        y = vector.y
    }

    fun getPosition() = PointD(x, y)
}
