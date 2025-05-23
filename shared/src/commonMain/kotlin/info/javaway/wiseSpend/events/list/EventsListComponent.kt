package info.javaway.wiseSpend.events.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.common.ui.calendar.model.CalendarDay
import info.javaway.wiseSpend.events.creation.CreateEventComponent
import info.javaway.wiseSpend.events.models.SpendEvent
import kotlinx.coroutines.flow.StateFlow

interface EventsListComponent {

    val model: StateFlow<EventsScreenContract.State>
    val slot: Value<ChildSlot<*, CreateEventComponent>>

    fun selectDay(calendarDay: CalendarDay)
    fun newEvent(calendarDay: CalendarDay?)
    fun onDismiss()
    fun createEvent(newEvent: SpendEvent)

    interface Factory {
        fun create(componentContext: ComponentContext): EventsListComponent
    }
}