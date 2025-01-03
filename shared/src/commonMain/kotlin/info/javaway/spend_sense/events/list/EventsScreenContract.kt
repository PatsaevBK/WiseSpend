package info.javaway.spend_sense.events.list

import info.javaway.spend_sense.base.BaseViewEvent
import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.categories.models.Category
import info.javaway.spend_sense.common.ui.calendar.model.CalendarDay
import info.javaway.spend_sense.common.ui.calendar.model.CalendarLabel
import info.javaway.spend_sense.events.extensions.toCalendarLabel
import info.javaway.spend_sense.events.extensions.toUI
import info.javaway.spend_sense.events.models.SpendEvent
import info.javaway.spend_sense.events.models.SpendEventUI

interface EventsScreenContract {
    data class State(
        val selectedDay: CalendarDay?,
        val events: List<SpendEvent>,
        val categories: List<Category>,
    ) : BaseViewState {
        val eventsByDay: List<SpendEventUI>
            get() = events.filter { it.date == selectedDay?.date }
                .map { spendEvent ->
                    spendEvent.toUI(categories.firstOrNull { it.id == spendEvent.id }
                        ?: Category.NONE)
                }

        val calendarLabels: List<CalendarLabel>
            get() = events.map { event ->
                event.toCalendarLabel(category = categories.firstOrNull { it.id == event.categoryId }
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