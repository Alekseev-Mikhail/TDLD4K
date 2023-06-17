package tdld4k.player.camera

interface DebugObject {
    val debugItems: MutableMap<String, Any>
    fun updateDebugItems()
}