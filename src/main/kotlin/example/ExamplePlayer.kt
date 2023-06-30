package example

import tdld4k.debug.DebugObjectInterface
import tdld4k.player.Player
import java.text.DecimalFormat
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.Delegates

class ExamplePlayer(
    x: Double,
    y: Double,
    z: Double,
    xDirection: Double,
    yDirection: Double,
    fov: Double,
    quality: Double,
    renderDistance: Double,
    movementSpeed: Double,
    xRotationSpeed: Double,
    yRotationSpeed: Double,
    maxFps: Int,
    isFreezeMovement: Boolean,
    isFreezeRotation: Boolean,
) : Player(
    x,
    y,
    z,
    xDirection,
    yDirection,
    fov,
    quality,
    renderDistance,
    movementSpeed,
    xRotationSpeed,
    yRotationSpeed,
    maxFps,
    isFreezeMovement,
    isFreezeRotation,
),
    DebugObjectInterface {
    var isFullscreen = true
    var isEscape: Boolean by Delegates.observable(false) { _, _, _ -> listeners.forEach { e -> e.run() } }
    var isShowDebugMenu: Boolean by Delegates.observable(false) { _, _, _ -> listeners.forEach { e -> e.run() } }

    private val decForCoordinates = DecimalFormat("0.000")
    private val decForXDirection = DecimalFormat("000.000")
    private val decForYDirection = DecimalFormat("0.000")
    private val decForFov = DecimalFormat("0")
    private val decForQuality = DecimalFormat("0")

    override val debugItems: MutableMap<String?, String?> = mutableMapOf(
        Pair(
            "FPS",
            fps.toString(),
        ),
        Pair(
            "X Y Z",
            "${decForCoordinates.format(x)}  ${decForCoordinates.format(y)}  ${decForCoordinates.format(z)}",
        ),
        Pair(
            "Pass",
            null,
        ),
        Pair(
            "Direction X  Direction Y",
            "${decForXDirection.format(xDirection)}  ${decForXDirection.format(yDirection)}",
        ),
        Pair(
            "FOV",
            decForFov.format(fov),
        ),
        Pair(
            "Quality",
            decForQuality.format(quality),
        ),
    )

    override fun updateDebugItems() {
        debugItems["FPS"] = fps.toString()
        debugItems["X Y Z"] =
            "${decForCoordinates.format(x)}  ${decForCoordinates.format(y)}  ${decForCoordinates.format(z)}"
        debugItems["Direction X  Direction Y"] =
            "${decForXDirection.format(xDirection)}  ${decForYDirection.format(yDirection)}"
        debugItems["FOV"] = decForFov.format(fov)
        debugItems["Quality"] = decForQuality.format(quality)
    }

    private val listeners = CopyOnWriteArrayList<Runnable>()

    fun addListenerForTechOptions(listener: Runnable) {
        listeners.add(listener)
    }
}
