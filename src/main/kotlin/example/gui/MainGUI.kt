package example.gui

import tdld4k.camera.window.Window
import tdld4k.camera.window.content.MainContent
import tdld4k.entity.Entity
import tdld4k.options.BasicOptions
import tdld4k.world.World

class MainGUI(
    world: World,
    entity: Entity,
    options: BasicOptions,
) : MainContent(world, entity, options) {
    override fun changeOn(window: Window) {
        super.changeOn(window)
    }
}
