package tdld4k.player

import java.lang.Math.toRadians
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.Delegates

open class Player(
    x: Double,
    y: Double,
    z: Double,
    xDirection: Double,
    yDirection: Double,
    fov: Double,
    quality: Double,
    renderDistance: Double,
    val movementSpeed: Double,
    val xRotationSpeed: Double,
    val yRotationSpeed: Double,
    val maxFps: Int,
    var isFreezeMovement: Boolean,
    var isFreezeRotation: Boolean,
) {
    var x: Double by Delegates.observable(x) { _, _, _ -> listeners.forEach { it.run() } }
    var y: Double by Delegates.observable(y) { _, _, _ -> listeners.forEach { it.run() } }
    var z: Double by Delegates.observable(z) { _, _, _ -> listeners.forEach { it.run() } }
    var xDirection: Double by Delegates.observable(xDirection) { _, _, _ -> listeners.forEach { it.run() } }
    var yDirection: Double by Delegates.observable(yDirection) { _, _, _ -> listeners.forEach { it.run() } }
    var fov: Double by Delegates.observable(fov) { _, _, _ -> listeners.forEach { it.run() } }
    var quality: Double by Delegates.observable(quality) { _, _, _ -> listeners.forEach { it.run() } }
    var renderDistance: Double by Delegates.observable(renderDistance) { _, _, _ -> listeners.forEach { it.run() } }
    var fps = 0

    private val listeners = CopyOnWriteArrayList<Runnable>()

    fun addListener(listener: Runnable) {
        listeners.add(listener)
    }

    fun translateToRadians() = TranslatedToRadians(toRadians(xDirection), toRadians(fov))
}
