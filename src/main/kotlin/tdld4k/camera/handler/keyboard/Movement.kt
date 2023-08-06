package tdld4k.camera.handler.keyboard

import tdld4k.entity.Creature
import tdld4k.options.MovementOptions
import tdld4k.util.ray.ray
import tdld4k.world.World
import java.lang.Math.toRadians
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

abstract class Movement : KeyboardHandler() {
    abstract val world: World
    abstract val creature: Creature
    abstract val options: MovementOptions
    private val movement = Timer()
    private lateinit var forwardTask: TimerTask
    private lateinit var backTask: TimerTask
    private lateinit var leftTask: TimerTask
    private lateinit var rightTask: TimerTask

    override fun keyPress(action: KeyAction) {
        if (action.window.content.isMain) {
            when (action.key) {
                options.forwardKey.value -> forwardTask = createTask(0)
                options.backKey.value -> backTask = createTask(180)
                options.leftKey.value -> leftTask = createTask(-90)
                options.rightKey.value -> rightTask = createTask(90)
                else -> {}
            }
        }
    }

    override fun keyRelease(action: KeyAction) {
        if (action.window.content.isMain) {
            when (action.key) {
                options.forwardKey.value -> {
                    forwardTask.cancel()
                    movement.purge()
                }

                options.backKey.value -> {
                    backTask.cancel()
                    movement.purge()
                }

                options.leftKey.value -> {
                    leftTask.cancel()
                    movement.purge()
                }

                options.rightKey.value -> {
                    rightTask.cancel()
                    movement.purge()
                }

                else -> {}
            }
        }
    }

    private fun createTask(plus: Int) =
        movement.schedule(0, 16) {
            creature.translate(
                ray(
                    world,
                    creature.getPosition(),
                    creature.movementSpeed,
                    toRadians(creature.horizontalDirection + plus),
                ).rayPointD,
            )
        }
}
