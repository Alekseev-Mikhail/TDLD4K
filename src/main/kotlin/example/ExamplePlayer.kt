package example

import tdld4k.player.Player
import tdld4k.player.camera.DebugObject
import java.text.DecimalFormat

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
),
    DebugObject {
    private val df = DecimalFormat("#.###")

    override val debugItems: MutableMap<String, Any> = mutableMapOf(
        Pair("X", df.format(x)),
        Pair("Y", df.format(y)),
        Pair("Direction", df.format(direction)),
        Pair("FOV", df.format(fov)),
        Pair("Quality", df.format(quality)),
    )

    override fun updateDebugItems() {
        debugItems["X"] = df.format(x)
        debugItems["Y"] = df.format(y)
        debugItems["Direction"] = df.format(direction)
        debugItems["FOV"] = df.format(fov)
        debugItems["Quality"] = df.format(quality)
    }
}
