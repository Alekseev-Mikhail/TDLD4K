package tdld4k.debug

interface DebugObject {
    val debugItems: MutableMap<String, String>
    fun updateDebugItems()
}
