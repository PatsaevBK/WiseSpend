package info.javaway.spend_sense.events.list

import com.arkivanov.decompose.ComponentContext
import info.javaway.spend_sense.common.ui.calendar.model.CalendarDay
import info.javaway.spend_sense.events.models.SpendEvent
import kotlinx.coroutines.flow.StateFlow

interface EventsListComponent {

    val model: StateFlow<EventsScreenContract.State>

    fun selectDay(calendarDay: CalendarDay)
    fun createEvent(newEvent: SpendEvent)

    interface Factory {
        fun create(componentContext: ComponentContext): EventsListComponent
    }
}