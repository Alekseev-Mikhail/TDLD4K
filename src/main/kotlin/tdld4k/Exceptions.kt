package tdld4k

import java.lang.IllegalArgumentException

fun tileMustBeNotNull(): Nothing = throw NullPointerException("Tile must be not null when getting side")

fun qualityMustBeLess(): Nothing = throw IllegalArgumentException("Quality must be less than half of wall size")
