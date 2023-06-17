package example

import tdld4k.player.Player
import tdld4k.player.camera.DebugObject

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
    isShowDebugMenu: Boolean,
) : Player(
    x,
    y,
    direction,
    fov,
    quality,
    renderDistance,
    isShowDebugMenu,
    movementSpeed,
    rotationSpeed,
    maxFps,
    isFreezeMovement,
    isFreezeRotation,
), DebugObject {
    override val debugItems: MutableMap<String, Any> = mutableMapOf(
        Pair("X", x),
        Pair("Y", y),
        Pair("Direction", direction),
        Pair("FOV", fov),
        Pair("Quality", quality),
    )

    override fun updateDebugItems() {
        debugItems["X"] = x
        debugItems["Y"] = y
        debugItems["Direction"] = direction
        debugItems["FOV"] = fov
        debugItems["Quality"] = quality
    }
}
