package info.javaway.wiseSpend.extensions

import io.github.aakira.napier.Napier

fun appLog(message: String) {
    Napier.d("SpendSense: $message")
}