package tdld4k.math

import tdld4k.world.GameShape

data class GamePointOfRayOutput(val vector: Vector2Double, val shape: GameShape?, val isWall: Boolean)