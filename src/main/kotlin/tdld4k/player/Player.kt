package tdld4k.player

import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.Delegates

open class Player(
    x: Double,
    y: Double,
    z: Double,
    direction: Double,
    directionY: Double,
    fov: Double,
    quality: Double,
    renderDistance: Double,
    val movementSpeed: Double,
    val rotationSpeed: Double,
    val maxFps: Int,
    var isFreezeMovement: Boolean,
    var isFreezeRotation: Boolean,
) {
    var x: Double by Delegates.observable(x) { _, _, _ -> listeners.forEach { it.run() } }
    var y: Double by Delegates.observable(y) { _, _, _ -> listeners.forEach { it.run() } }
    var z: Double by Delegates.observable(z) { _, _, _ -> listeners.forEach { it.run() } }
    var direction: Double by Delegates.observable(direction) { _, _, _ -> listeners.forEach { it.run() } }
    var directionY: Double by Delegates.observable(directionY) { _, _, _ -> listeners.forEach { it.run() } }
    var fov: Double by Delegates.observable(fov) { _, _, _ -> listeners.forEach { it.run() } }
    var quality: Double by Delegates.observable(quality) { _, _, _ -> listeners.forEach { it.run() } }
    var renderDistance: Double by Delegates.observable(renderDistance) { _, _, _ -> listeners.forEach { it.run() } }
    var fps: Int by Delegates.observable(0) { _, _, _ -> listeners.forEach { it.run() } }

    private val listeners = CopyOnWriteArrayList<Runnable>()

    fun addListener(listener: Runnable) {
        listeners.add(listener)
    }

    fun translateToRadians(): TranslatedToRadians {
        return TranslatedToRadians(Math.toRadians(direction), Math.toRadians(fov))
    }
}
