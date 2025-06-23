package info.javaway.wiseSpend.features.events.creation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.accounts.data.AccountRepository
import info.javaway.wiseSpend.features.accounts.list.AccountsListComponent
import info.javaway.wiseSpend.features.accounts.list.AccountsListComponentImpl
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.categories.data.CategoriesRepository
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponent
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponentImpl
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.events.creation.CreateEventContract.State
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.platform.randomUUID
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

class CreateEventComponentImpl(
    componentContext: ComponentContext,
    initialDate: CalendarDay?,
    spendEvent: SpendEvent?,
    private val categoriesRepository: CategoriesRepository,
    private val accountsRepository: AccountRepository,
    private val onSave: (SpendEvent) -> Unit,
) : CreateEventComponent, ComponentContext by componentContext {

    private val _model: MutableStateFlow<State> = MutableStateFlow(
        chooseMode(initialDate, spendEvent)
    )

    override val model: StateFlow<State> = _model.asStateFlow()

    private val categoriesNav = SlotNavigation<CategoryConfig>()
    override val categorySlot: Value<ChildSlot<*, CategoriesListComponent>> = childSlot(
        source = categoriesNav,
        serializer = CategoryConfig.serializer(),
        key = "CategorySlot",
        childFactory = { _, ctx ->
            CategoriesListComponentImpl(
                componentContext = ctx,
                categoriesRepository = categoriesRepository,
            )
        }
    )

    private val accountsNav = SlotNavigation<AccountConfig>()
    override val accountSlot: Value<ChildSlot<*, AccountsListComponent>> = childSlot(
        source = accountsNav,
        serializer = AccountConfig.serializer(),
        key = "AccountSlot",
        childFactory = { _, ctx ->
            AccountsListComponentImpl(
                componentContext = ctx,
                accountRepository = accountsRepository,
            )
        }
    )

    override fun selectDate(date: LocalDate?) = _model.update { it.copy(date = date ?: LocalDate.now()) }
    override fun changeTitle(title: String) = _model.update { it.copy(title = title) }
    override fun changeNote(note: String) = _model.update { it.copy(note = note) }
    override fun changeCost(cost: String) = _model.update { it.copy(cost = cost.toDoubleOrNull() ?: it.cost) }

    override fun selectCategory(category: Category) = _model.update { it.copy(selectedCategory = category) }
    override fun showCategory() = categoriesNav.activate(CategoryConfig)
    override fun dismissCategory() = categoriesNav.dismiss()

    override fun selectAccount(accountId: String) {
        accountsRepository.getById(accountId)?.let { account ->
            _model.update { old ->
                old.copy(selectedAccount = account)
            }
        }
    }
    override fun showAccount() = accountsNav.activate(AccountConfig)
    override fun dismissAccount() = accountsNav.dismiss()


    override fun finish() {
        val spendEvent = with(model.value) {
            val now = LocalDateTime.now()
            SpendEvent(
                id = eventId,
                title = title,
                accountId = selectedAccount.id,
                categoryId = selectedCategory.id,
                cost = cost,
                date = date,
                createdAt = createdAt,
                updatedAt = now,
                note = note
            )
        }
        resetState()
        onSave.invoke(spendEvent)
    }

    private fun resetState() = _model.update { State.NONE }

    private fun chooseMode(
        initialDate: CalendarDay?,
        spendEvent: SpendEvent?
    ): State {
        return if (spendEvent != null) {
            val selectedCategory = categoriesRepository.getById(spendEvent.categoryId)
            val selectedAccount = accountsRepository.getById(spendEvent.accountId)
            State(
                eventId = spendEvent.id,
                title = spendEvent.title,
                selectedCategory = selectedCategory ?: Category.NONE,
                categories = categoriesRepository.getAll(),
                selectedAccount = selectedAccount ?: Account.DEFAULT,
                date = spendEvent.date,
                createdAt = spendEvent.createdAt,
                cost = spendEvent.cost,
                note = spendEvent.note,
            )
        } else {
            State(
                eventId = randomUUID(),
                title = "",
                selectedCategory = Category.NONE,
                categories = categoriesRepository.getAll(),
                selectedAccount = accountsRepository.getById(Account.DEFAULT_ID) ?: Account.DEFAULT,
                date = initialDate?.date ?: LocalDate.now(),
                createdAt = LocalDateTime.now(),
                cost = 0.0,
                note = ""
            )
        }
    }

    @Serializable
    private data object CategoryConfig

    @Serializable
    private data object AccountConfig
}