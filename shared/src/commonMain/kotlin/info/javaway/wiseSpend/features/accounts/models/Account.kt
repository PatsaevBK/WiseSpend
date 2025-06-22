package info.javaway.wiseSpend.features.accounts.models

import info.javaway.wiseSpend.extensions.now
import kotlinx.datetime.LocalDateTime

data class Account(
    val id: String,
    val name: String,
    val amount: Double,
    val currency: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        const val DEFAULT_ID = "Default"
        const val DEFAULT_CURRENCY = 0x20BD.toChar().toString()

        val DEFAULT = Account(
            id = DEFAULT_ID,
            name = "Default",
            amount = 0.0,
            currency = DEFAULT_CURRENCY,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}