package example

import tdld4k.player.Player

class Player(
    x: Double,
    y: Double,
    direction: Double,
    fov: Double,
    quality: Double,
    renderDistance: Double,
    movementSpeed: Double,
    rotationSpeed: Double,
    maxFps: Int,
    isFreezeMovement: Boolean,
    isFreezeRotation: Boolean,
) : Player(
    x,
    y,
    direction,
    fov,
    quality,
    renderDistance,
    movementSpeed,
    rotationSpeed,
    maxFps,
    isFreezeMovement,
    isFreezeRotation,
)
