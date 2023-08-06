package tdld4k.camera.handler.mouse

import tdld4k.entity.Creature

abstract class Rotation : MouseHandler() {
    abstract val creature: Creature

    override fun motion(motion: MouseMotion) {
        if (motion.window.content.isMain) {
            creature.horizontalDirection += (motion.x - motion.lastX) * creature.horizontalSpeed
            creature.verticalDirection += (motion.lastY - motion.y) * creature.verticalSpeed
        }
    }
}
