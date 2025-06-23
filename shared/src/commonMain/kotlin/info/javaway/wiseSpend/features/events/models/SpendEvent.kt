package info.javaway.wiseSpend.features.events.models

import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SpendEvent(
    val id: String,
    val categoryId: String,
    val accountId: String,
    val title: String,
    val cost: Double,
    val date: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val note: String,
) {
    companion object {
        val NONE = SpendEvent(
            id = "",
            categoryId = "",
            accountId = Account.DEFAULT_ID,
            title = "",
            cost = 0.0,
            date = LocalDate.now(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            note = ""
        )
    }
}
