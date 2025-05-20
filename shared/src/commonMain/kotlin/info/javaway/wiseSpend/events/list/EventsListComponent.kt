package info.javaway.wiseSpend.events.list

import com.arkivanov.decompose.ComponentContext
import info.javaway.wiseSpend.common.ui.calendar.model.CalendarDay
import info.javaway.wiseSpend.events.models.SpendEvent
import kotlinx.coroutines.flow.StateFlow

interface EventsListComponent {

    val model: StateFlow<EventsScreenContract.State>

    fun selectDay(calendarDay: CalendarDay)
    fun createEvent(newEvent: SpendEvent)

    interface Factory {
        fun create(componentContext: ComponentContext): EventsListComponent
    }
}