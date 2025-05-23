package info.javaway.wiseSpend.platform

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.DONUT)
actual class DeviceInfo actual constructor() {
    private val displayMetrics = Resources.getSystem().displayMetrics

    actual val osName: String = "Android"
    actual val osVersion: String = "${Build.VERSION.SDK_INT}"
    actual val model: String = "${Build.MANUFACTURER} ${Build.MODEL}"
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    actual val cpu: String = Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown"
    actual val screenWidth: String = displayMetrics.widthPixels.toString()
    actual val screenHeight: String = displayMetrics.heightPixels.toString()
    actual val screenDestiny: String = displayMetrics.density.roundToInt().toString()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    actual fun getSummary(): String = buildString {
         appendLine("osName = $osName")
         appendLine("osVersion = $osVersion")
         appendLine("model = $model")
         appendLine("cpu = $cpu")
         appendLine("screenWidth = $screenWidth")
         appendLine("screenHeight = $screenHeight")
         appendLine("screenDestiny = $screenDestiny")
    }

}