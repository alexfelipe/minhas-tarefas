import platform.Foundation.NSUUID.Companion.UUID

actual fun generateUUID(): String {
    return UUID().UUIDString.lowercase()
}