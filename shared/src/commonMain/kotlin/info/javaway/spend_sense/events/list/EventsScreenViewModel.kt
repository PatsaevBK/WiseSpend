package info.javaway.spend_sense.events.list

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.common.ui.calendar.model.CalendarDay
import info.javaway.spend_sense.events.EventsRepository
import info.javaway.spend_sense.events.models.SpendEvent
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class EventsScreenViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val eventsRepository: EventsRepository,
) : BaseViewModel<EventsScreenContract.State, EventsScreenContract.Events>() {

    init {
        activate()
    }

    override fun initialState(): EventsScreenContract.State = EventsScreenContract.State.NONE

    fun selectDay(calendarDay: CalendarDay) = updateState { copy(selectedDay = calendarDay) }

    fun createEvent(newEvent: SpendEvent) {
        viewModelScope.launch {
            eventsRepository.create(newEvent)
        }
    }

    private fun activate() {
        combine(
            eventsRepository.getAllFlow(),
            categoriesRepository.getAllFlow(),
        ) { spendEvents, categories ->
            updateState { copy(events = spendEvents, categories = categories) }
        }.launchIn(viewModelScope)
    }
}
