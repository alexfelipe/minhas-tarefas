import platform.Foundation.NSUUID.Companion.UUID

actual fun getPlatformName(): String = "iOS"
actual fun generateUUID(): String = UUID().UUIDString.lowercase()