package info.javaway.wiseSpend.features.events.list

import info.javaway.wiseSpend.base.BaseViewEvent
import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.features.events.extensions.toCalendarLabel
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.events.extensions.toUI
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.features.events.models.SpendEventUI
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarDay
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarLabel

interface EventsScreenContract {
    data class State(
        val selectedDay: CalendarDay?,
        val events: List<SpendEvent>,
        val categories: List<Category>,
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

        companion object {
            val NONE = State(
                selectedDay = null,
                events = emptyList(),
                categories = emptyList(),
            )
        }
    }

    sealed interface Events : BaseViewEvent {

    }
}