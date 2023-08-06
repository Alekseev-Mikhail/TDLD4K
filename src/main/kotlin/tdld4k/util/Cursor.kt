package tdld4k.util

data class Cursor(val name: String) {
    var id: Long? = null
        set(value) {
            if (field == null) field = value
        }
}
