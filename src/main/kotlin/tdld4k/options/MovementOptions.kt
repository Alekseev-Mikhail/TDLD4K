package tdld4k.options

import tdld4k.camera.handler.Key

interface MovementOptions : BasicOptions {
    var forwardKey: Key
    var backKey: Key
    var leftKey: Key
    var rightKey: Key
}
