import java.util.*

actual fun getPlatformName(): String = "Android"

actual fun generateUUID(): String = UUID.randomUUID().toString().lowercase()