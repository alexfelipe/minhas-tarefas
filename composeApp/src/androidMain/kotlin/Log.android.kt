import android.util.Log

actual fun logInfo(
    tag: String,
    message: String
) {
    Log.i(tag, message)
}