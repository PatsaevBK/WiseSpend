package info.javaway.spend_sense.platform

actual class DeviceInfo actual constructor(){
    actual val osName: String = System.getProperty("os.name") ?: "Desktop"
    actual val osVersion: String = System.getProperty("os.version") ?: "Unknown version"
    actual val model: String = "Desktop"
    actual val cpu: String = System.getProperty("os.arch") ?: "Unknown architecture"
    actual val screenWidth: String = ""
    actual val screenHeight: String = ""
    actual val screenDestiny: String = ""

    actual fun getSummary(): String = buildString {
        appendLine("osName = $osName")
        appendLine("osVersion = $osVersion")
        appendLine("model = $model")
        appendLine("cpu = $cpu")
    }

}