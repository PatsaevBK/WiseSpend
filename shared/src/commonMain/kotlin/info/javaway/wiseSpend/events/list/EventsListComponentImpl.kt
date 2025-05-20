package info.javaway.wiseSpend.events.list

import com.arkivanov.decompose.ComponentContext
import info.javaway.wiseSpend.categories.CategoriesRepository
import info.javaway.wiseSpend.common.ui.calendar.model.CalendarDay
import info.javaway.wiseSpend.events.EventsRepository
import info.javaway.wiseSpend.events.extensions.componentScope
import info.javaway.wiseSpend.events.models.SpendEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventsListComponentImpl(
    componentContext: ComponentContext,
    private val categoriesRepository: CategoriesRepository,
    private val eventsRepository: EventsRepository,
) : EventsListComponent, ComponentContext by componentContext {

    private val _model = MutableStateFlow(EventsScreenContract.State.NONE)
    override val model: StateFlow<EventsScreenContract.State> = _model.asStateFlow()

    private val scope = componentScope()

    init {
        combine(
            eventsRepository.getAllFlow(),
            categoriesRepository.getAllFlow(),
        ) { spendEvents, categories ->
            _model.update { it.copy(events = spendEvents, categories = categories) }
        }.launchIn(scope)
    }

    override fun selectDay(calendarDay: CalendarDay) {
        _model.update { it.copy(selectedDay = calendarDay) }
    }

    override fun createEvent(newEvent: SpendEvent) {
        scope.launch {
            eventsRepository.create(newEvent)
        }
    }

    class Factory(
        private val categoriesRepository: CategoriesRepository,
        private val eventsRepository: EventsRepository,
    ) : EventsListComponent.Factory {
        override fun create(componentContext: ComponentContext): EventsListComponent {
            return EventsListComponentImpl(
                componentContext = componentContext,
                categoriesRepository = categoriesRepository,
                eventsRepository = eventsRepository
            )
        }
    }
}