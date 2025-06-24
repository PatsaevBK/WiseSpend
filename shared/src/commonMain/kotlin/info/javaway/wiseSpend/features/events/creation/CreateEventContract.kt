package info.javaway.wiseSpend.features.events.creation

import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.platform.randomUUID
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

interface CreateEventContract {

    data class State(
        val eventId: String,
        val title: String,
        val selectedCategory: Category,
        val categories: List<Category>,
        val selectedAccount: Account,
        val date: LocalDate,
        val createdAt: LocalDateTime,
        val cost: Double,
        val note: String
    ) : BaseViewState {

        val isCategoriesEmpty: Boolean
            get() = categories.isEmpty()

        val isEventValid: Boolean
            get() = title.isNotEmpty() && (selectedAccount.amount - cost) >= 0

        companion object {
            val NONE = State(
                eventId = randomUUID(),
                title = "",
                selectedCategory = Category.NONE,
                selectedAccount = Account.DEFAULT,
                categories = emptyList(),
                createdAt = LocalDateTime.now(),
                date = LocalDate.now(),
                cost = 0.0,
                note = ""
            )
        }
    }
}