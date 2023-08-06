package example

import tdld4k.camera.handler.Key
import tdld4k.options.MovementOptions

data class Settings(
    override var fov: Double,
    override var quality: Double,
    override var renderDistance: Double,
    override var forwardKey: Key,
    override var backKey: Key,
    override var leftKey: Key,
    override var rightKey: Key,
) : MovementOptions
