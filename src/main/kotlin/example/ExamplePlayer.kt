package example

import tdld4k.player.GamePlayer

class ExamplePlayer(
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
) : GamePlayer(
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
