package example

import tdld4k.debug.DebugObjectInterface
import tdld4k.entity.Creature
import java.text.DecimalFormat
import java.util.concurrent.CopyOnWriteArrayList

class Player(
    override var x: Double,
    override var y: Double,
    override var z: Double,
    override var movementSpeed: Double,
    override var horizontalSpeed: Double,
    override var verticalSpeed: Double,
) : Creature(), DebugObjectInterface {

    private val decForCoordinates = DecimalFormat("0.000")
    private val decForXDirection = DecimalFormat("000.000")
    private val decForYDirection = DecimalFormat("0.000")

    override val debugItems: MutableMap<String?, String?> = mutableMapOf(
        "X Y Z" to "${decForCoordinates.format(x)}  ${decForCoordinates.format(y)}  ${decForCoordinates.format(z)}",
        "Pass" to null,
        "Direction X  Direction Y" to
            "${decForXDirection.format(horizontalDirection)}  ${decForYDirection.format(verticalDirection)}",
    )

    override fun updateDebugItems() {
        debugItems["X Y Z"] =
            "${decForCoordinates.format(x)}  ${decForCoordinates.format(z)}  ${decForCoordinates.format(y)}"
        debugItems["Direction X  Direction Y"] =
            "${decForXDirection.format(horizontalDirection)}  ${decForYDirection.format(verticalDirection)}"
    }

    private val listeners = CopyOnWriteArrayList<Runnable>()

    fun addListenerForTechOptions(listener: Runnable) {
        listeners.add(listener)
    }
}
