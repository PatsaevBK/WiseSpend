package info.javaway.wiseSpend.features.events.list

import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.features.accounts.data.findSelectedAccount
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.accounts.models.AccountUi
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.events.data.toCalendarLabel
import info.javaway.wiseSpend.features.events.data.toUI
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.features.events.models.SpendEventUI
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarDay
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarLabel

interface EventsScreenContract {
    data class State(
        val selectedDay: CalendarDay?,
        val events: List<SpendEvent>,
        val categories: List<Category>,
        val accounts: List<Account>,
        val selectedAccountId: String?,
    ) : BaseViewState {
        val eventsByDay: List<SpendEventUI>
            get() = events.filter { it.date == selectedDay?.date }
                .map { spendEvent ->
                    spendEvent.toUI(categories.firstOrNull { it.id == spendEvent.categoryId }
                        ?: Category.NONE)
                }

        val calendarLabels: List<CalendarLabel>
            get() = events.map { spendEvent ->
                spendEvent.toCalendarLabel(category = categories.firstOrNull { it.id == spendEvent.categoryId }
                    ?: Category.NONE)
            }

        val selectedAccountUi: AccountUi
            get() = findSelectedAccount(accounts, selectedAccountId)

        companion object {
            const val TOTAL_ACCOUNTS_NAME = "Total"

            val NONE = State(
                selectedDay = null,
                events = emptyList(),
                categories = emptyList(),
                accounts = emptyList(),
                selectedAccountId = null,
            )
        }
    }
}