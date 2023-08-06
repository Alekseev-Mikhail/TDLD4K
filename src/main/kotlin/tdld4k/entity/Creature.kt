package tdld4k.entity

abstract class Creature : Entity() {
    abstract var movementSpeed: Double
    abstract var horizontalSpeed: Double
    abstract var verticalSpeed: Double
}
