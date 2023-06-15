package example

import tdld4k.player.GamePlayer

class ExamplePlayer(
    isFreezeMovement: Boolean,
    isFreezeRotation: Boolean,
    movementSpeed: Double,
    rotationSpeed: Double,
    maxFps: Int,
    x: Double,
    y: Double,
    direction: Double,
    fov: Double,
    quality: Double,
    renderDistance: Double,
) : GamePlayer(
    isFreezeMovement,
    isFreezeRotation,
    movementSpeed,
    rotationSpeed,
    maxFps,
    x,
    y,
    direction,
    fov,
    quality,
    renderDistance,
)
