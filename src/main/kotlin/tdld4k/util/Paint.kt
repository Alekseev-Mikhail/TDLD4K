package tdld4k.util

val WHITE = Paint(255, 255, 255)
val BLACK = Paint(0, 0, 0)

data class Paint(val red: Int, val green: Int, val blue: Int, val alpha: Int) {
    constructor(red: Int, green: Int, blue: Int) : this(red, green, blue, 255)
}
