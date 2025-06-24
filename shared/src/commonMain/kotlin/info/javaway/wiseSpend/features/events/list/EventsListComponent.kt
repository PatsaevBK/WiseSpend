package info.javaway.wiseSpend.features.events.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.features.accounts.list.AccountsListComponent
import info.javaway.wiseSpend.features.events.creation.CreateEventComponent
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarDay
import kotlinx.coroutines.flow.StateFlow

interface EventsListComponent {

    val model: StateFlow<EventsScreenContract.State>
    val createEventSlot: Value<ChildSlot<*, CreateEventComponent>>
    val accountsSlot: Value<ChildSlot<*, AccountsListComponent>>

    fun selectDay(calendarDay: CalendarDay)
    fun newEvent(calendarDay: CalendarDay?)
    fun editEvent(eventId: String)

    fun onEventDismiss()

    fun showAccounts()
    fun onAccountsDismiss()
    fun selectAccount(id: String?)

    interface Factory {
        fun create(componentContext: ComponentContext): EventsListComponent
    }
}