package info.javaway.wiseSpend.features.events.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.features.events.creation.CreateEventComponent
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarDay
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