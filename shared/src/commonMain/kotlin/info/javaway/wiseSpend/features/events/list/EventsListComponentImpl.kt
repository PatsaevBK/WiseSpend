package info.javaway.wiseSpend.features.events.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.extensions.componentScope
import info.javaway.wiseSpend.features.categories.data.CategoriesRepository
import info.javaway.wiseSpend.features.events.creation.CreateEventComponent
import info.javaway.wiseSpend.features.events.creation.CreateEventComponentImpl
import info.javaway.wiseSpend.features.events.data.EventsRepository
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class EventsListComponentImpl(
    componentContext: ComponentContext,
    private val categoriesRepository: CategoriesRepository,
    private val eventsRepository: EventsRepository,
) : EventsListComponent, ComponentContext by componentContext {

    private val scope = componentScope()

    private val _model = MutableStateFlow(EventsScreenContract.State.NONE)
    override val model: StateFlow<EventsScreenContract.State> = _model.asStateFlow()

    private val nav = SlotNavigation<Config>()
    override val slot: Value<ChildSlot<*, CreateEventComponent>> = childSlot(
        source = nav,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = { config, ctx ->
            CreateEventComponentImpl(
                initialDate = config.calendarDay,
                categoriesRepository = categoriesRepository,
                componentContext = ctx,
                onSave = {
                    scope.launch { eventsRepository.create(it) }
                    nav.dismiss()
                }
            )
        }
    )


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

    override fun newEvent(calendarDay: CalendarDay?) = nav.activate(Config(calendarDay))

    override fun onDismiss() = nav.dismiss()

    @Serializable
    private data class Config(val calendarDay: CalendarDay?)

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