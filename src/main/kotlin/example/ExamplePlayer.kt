package example

import tdld4k.debug.DebugObject
import tdld4k.player.Player
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
    private val decForCoordinates = DecimalFormat("0.000")
    private val decForDirection = DecimalFormat("000.000")
    private val decForFov = DecimalFormat("0")
    private val decForQuality = DecimalFormat("0.00000")

    override val debugItems: MutableMap<String?, String?> = mutableMapOf(
        Pair("FPS", fps.toString()),
        Pair("X Y", "${decForCoordinates.format(x)} ${decForCoordinates.format(y)}"),
        Pair("Pass", null),
        Pair("Direction", decForDirection.format(direction)),
        Pair("FOV", decForFov.format(fov)),
        Pair("Quality", decForQuality.format(quality)),
    )

    override fun updateDebugItems() {
        debugItems["FPS"] = fps.toString()
        debugItems["X Y"] = "${decForCoordinates.format(x)} ${decForCoordinates.format(y)}"
        debugItems["Direction"] = decForDirection.format(direction)
        debugItems["FOV"] = decForFov.format(fov)
        debugItems["Quality"] = decForQuality.format(quality)
    }
}
