package info.javaway.wiseSpend.platform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.CoreGraphics.CGRectGetHeight
import platform.CoreGraphics.CGRectGetWidth
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithCString
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceIdiomPad
import platform.UIKit.UIUserInterfaceIdiomPhone
import platform.posix.uname
import platform.posix.utsname
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual class DeviceInfo actual constructor() {
    actual val osName: String = when (UIDevice.currentDevice.userInterfaceIdiom) {
        UIUserInterfaceIdiomPad -> "iPadOs"
        UIUserInterfaceIdiomPhone -> "iPhoneOs"
        else -> Platform.osFamily.name
    }
    actual val osVersion: String = UIDevice.currentDevice.systemVersion
    @OptIn(ExperimentalForeignApi::class)
    actual val model: String = memScoped {
        val systemInfo: utsname = alloc()
        uname(systemInfo.ptr)
        return@memScoped NSString.stringWithCString(
            systemInfo.machine,
            encoding = NSUTF8StringEncoding
        ) ?: "Unknown model"
    }
    actual val cpu: String = Platform.cpuArchitecture.name
    @OptIn(ExperimentalForeignApi::class)
    actual val screenWidth: String = CGRectGetWidth(UIScreen.mainScreen.nativeBounds).toString()
    @OptIn(ExperimentalForeignApi::class)
    actual val screenHeight: String = CGRectGetHeight(UIScreen.mainScreen.nativeBounds).toString()
    actual val screenDestiny: String = UIScreen.mainScreen.scale.toString()

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