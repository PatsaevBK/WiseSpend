package info.javaway.wiseSpend.features.events.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.extensions.componentScope
import info.javaway.wiseSpend.features.accounts.data.AccountRepository
import info.javaway.wiseSpend.features.accounts.list.AccountsListComponent
import info.javaway.wiseSpend.features.accounts.list.AccountsListComponentImpl
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
    private val accountsRepository: AccountRepository,
) : EventsListComponent, ComponentContext by componentContext {

    private val scope = componentScope()

    private val _model = MutableStateFlow(EventsScreenContract.State.NONE)
    override val model: StateFlow<EventsScreenContract.State> = _model.asStateFlow()

    private val createEventNav = SlotNavigation<EventConfig>()
    override val createEventSlot: Value<ChildSlot<*, CreateEventComponent>> = childSlot(
        source = createEventNav,
        serializer = EventConfig.serializer(),
        handleBackButton = true,
        key = "EventSlot",
        childFactory = { config, ctx ->
            val (initialDate, event) = when (config) {
                is EventConfig.CreateEventConfig -> Pair(config.calendarDay, null)
                is EventConfig.EditEventConfig -> Pair(null, config.event)
            }

            CreateEventComponentImpl(
                initialDate = initialDate,
                spendEvent = event,
                categoriesRepository = categoriesRepository,
                accountsRepository = accountsRepository,
                componentContext = ctx,
                onSave = { spendEvent ->
                    scope.launch {
                        when (config) {
                            is EventConfig.CreateEventConfig ->
                                createEventAndUpdateAccount(spendEvent)
                            is EventConfig.EditEventConfig ->
                                editExistedEventAndUpdateAccount(oldEvent = config.event, newEvent = spendEvent)
                        }
                    }
                    createEventNav.dismiss()
                }
            )
        }
    )

    private val accountNav = SlotNavigation<AccountConfig>()
    override val accountsSlot: Value<ChildSlot<*, AccountsListComponent>> = childSlot(
        source = accountNav,
        serializer = AccountConfig.serializer(),
        key = "AccountsSlot",
        handleBackButton = true,
        childFactory = { _, ctx ->
            AccountsListComponentImpl(
                componentContext = ctx,
                accountRepository = accountsRepository,
            )
        }
    )

    init {
        combine(
            eventsRepository.getAllFlow(),
            categoriesRepository.getAllFlow(),
            accountsRepository.getAllFlow(),
        ) { spendEvents, categories, accounts ->
            _model.update { it.copy(events = spendEvents, categories = categories, accounts = accounts) }
        }.launchIn(scope)
    }

    override fun selectDay(calendarDay: CalendarDay) {
        _model.update { it.copy(selectedDay = calendarDay) }
    }

    override fun newEvent(calendarDay: CalendarDay?) =
        createEventNav.activate(EventConfig.CreateEventConfig(calendarDay))

    override fun editEvent(eventId: String) {
        eventsRepository.getById(eventId)?.let {
            createEventNav.activate(EventConfig.EditEventConfig(it))
        }
    }

    override fun onEventDismiss() = createEventNav.dismiss()

    override fun selectAccount(id: String?) {
        _model.update { it.copy(selectedAccountId = id) }
    }

    override fun showAccounts() {
        accountNav.activate(AccountConfig)
    }

    override fun onAccountsDismiss() = accountNav.dismiss()

    private suspend fun createEventAndUpdateAccount(spendEvent: SpendEvent) {
        eventsRepository.create(spendEvent)
        val oldAccount = accountsRepository.getById(spendEvent.accountId) ?: return
        val updatedAccount = oldAccount.copy(amount = oldAccount.amount - spendEvent.cost)
        accountsRepository.create(updatedAccount)
    }

    private suspend fun editExistedEventAndUpdateAccount(oldEvent: SpendEvent, newEvent: SpendEvent) {
        val oldAccount = accountsRepository.getById(oldEvent.accountId) ?: return
        val newAccount = accountsRepository.getById(newEvent.accountId) ?: return
        if (oldEvent.cost == newEvent.cost && oldAccount.id == newAccount.id) return

        TODO()
    }

    @Serializable
    private sealed interface EventConfig {
        data class CreateEventConfig(val calendarDay: CalendarDay?): EventConfig
        data class EditEventConfig(val event: SpendEvent): EventConfig
    }

    @Serializable
    private data object AccountConfig

    class Factory(
        private val categoriesRepository: CategoriesRepository,
        private val eventsRepository: EventsRepository,
        private val accountRepository: AccountRepository,
    ) : EventsListComponent.Factory {
        override fun create(componentContext: ComponentContext): EventsListComponent {
            return EventsListComponentImpl(
                componentContext = componentContext,
                categoriesRepository = categoriesRepository,
                eventsRepository = eventsRepository,
                accountsRepository = accountRepository,
            )
        }
    }
}