package tdld4k.debug

interface DebugObjectInterface {
    val debugItems: MutableMap<String?, String?>
    fun updateDebugItems()
}
