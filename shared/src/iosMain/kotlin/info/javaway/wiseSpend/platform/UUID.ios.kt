package info.javaway.wiseSpend.platform

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()