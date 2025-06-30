package info.javaway.wiseSpend.features.events.list

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.events.creation.CreateEventComponent
import info.javaway.wiseSpend.features.events.creation.CreateEventComponentImpl
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.mocks.AccountsRepositoryMock
import info.javaway.wiseSpend.mocks.CategoriesRepositoryMock
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CreateEventComponentTest {
    
    private lateinit var sut: CreateEventComponent
    private var accountsRepositoryMock: AccountsRepositoryMock = AccountsRepositoryMock()
    private var categoriesRepositoryMock: CategoriesRepositoryMock = CategoriesRepositoryMock()

    private val dispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `when initial event not null should event account be in state`() {
        val requiredAccountId = "RequiredAccountId"
        val initialEvent = SpendEvent.NONE.copy(id = "Required", accountId = requiredAccountId)
        accountsRepositoryMock.enqueueAccounts(Account.DEFAULT.copy(id = requiredAccountId))
        categoriesRepositoryMock.stubbedCategory = Category.NONE
        createComponent(initialEvent, null)

        val state = sut.model.value
        assertThat(state.selectedAccount.id).isEqualTo(requiredAccountId)
    }

    @Test
    fun `when initial event is null should default account be in state`() {
        val initialEvent = null
        createComponent(initialEvent, null)

        val state = sut.model.value
        assertThat(state.selectedAccount.id).isEqualTo(Account.DEFAULT.id)
    }

    @Test
    fun `when initial event is null should invoke getAccountById(defaultId)`() {
        val initialEvent = null
        createComponent(initialEvent, null)

        assertTrue(accountsRepositoryMock.getByIdInvoked)
        assertThat(accountsRepositoryMock.getByIdInvokedWithId).isEqualTo(Account.DEFAULT.id)
    }

    @Test
    fun `when invoke select account should invoke getAccountById(selectedId)`() {
        val initialEvent = null
        createComponent(initialEvent, null)

        assertTrue(accountsRepositoryMock.getByIdInvoked)
        assertThat(accountsRepositoryMock.getByIdInvokedWithId).isEqualTo(Account.DEFAULT.id)
    }

    @Test
    fun `when invoke selectDay should day be in state`() {
        val requiredDate = LocalDate.now()
        createComponent(null, null)

        sut.selectDate(requiredDate)

        val state = sut.model.value
        assertThat(state.date).isEqualTo(requiredDate)
    }

    @Test
    fun `when invoke changeTitle should title be in state`() {
        val requiredTitle = "requiredTitle"
        createComponent(null, null)

        sut.changeTitle(requiredTitle)

        val state = sut.model.value
        assertThat(state.title).isEqualTo(requiredTitle)
    }

    @Test
    fun `when invoke changeNote should note be in state`() {
        val requiredNote = "requiredTitle"
        createComponent(null, null)

        sut.changeNote(requiredNote)

        val state = sut.model.value
        assertThat(state.note).isEqualTo(requiredNote)
    }

    @Test
    fun `when invoke changeCost should note be in state`() {
        val requiredCost = "1234.1234"
        createComponent(null, null)

        sut.changeCost(requiredCost)

        val state = sut.model.value
        assertThat(state.cost.toString()).isEqualTo(requiredCost)
    }

    private fun createComponent(
        spendEvent: SpendEvent?,
        initialDate: CalendarDay?,
        onSave: (SpendEvent) -> Unit = {},
        stateKeeper: StateKeeper = StateKeeperDispatcher(),
        instanceKeeper: InstanceKeeper = InstanceKeeperDispatcher(),
    ) {
        val lifecycle = LifecycleRegistry()

        sut =
            CreateEventComponentImpl(
                componentContext = DefaultComponentContext(
                    lifecycle = lifecycle,
                    stateKeeper = stateKeeper,
                    instanceKeeper = instanceKeeper,
                ),
                categoriesRepository = categoriesRepositoryMock,
                accountsRepository = accountsRepositoryMock,
                initialDate = initialDate,
                spendEvent = spendEvent,
                onSave = onSave,
            )

        lifecycle.resume()
    }
}