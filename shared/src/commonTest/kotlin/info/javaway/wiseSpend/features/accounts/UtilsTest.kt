package info.javaway.wiseSpend.features.accounts

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.accounts.utils.createEventAndUpdateAccount
import info.javaway.wiseSpend.features.accounts.utils.editExistedEventAndUpdateAccount
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.mocks.AccountsRepositoryMock
import info.javaway.wiseSpend.mocks.EventsRepositoryMock
import kotlin.test.Test

internal class UtilsTest {

    private val accountsRepositoryMock = AccountsRepositoryMock()
    private val eventsRepositoryMock = EventsRepositoryMock()
    private var spendEvent = SpendEvent.NONE.copy(id = "test")

    @Test
    fun `createEventAndUpdateAccount creates spend event in events repository`() {
        val requiredEvent = spendEvent

        createEventAndUpdateAccount(
            spendEvent = spendEvent,
            eventsRepository = eventsRepositoryMock,
            accountsRepository = accountsRepositoryMock
        )

        assertThat(eventsRepositoryMock.isCreateInvoked).isTrue()
        assertThat(eventsRepositoryMock.createSpendEvent).isEqualTo(requiredEvent)
    }

    @Test
    fun `createEventAndUpdateAccount decreases account balance by spend amount`() {
        val initialAmount = 1_000.50
        val spendCost = 550.10
        val requiredAccountAmount = initialAmount - spendCost
        accountsRepositoryMock.enqueueAccounts(Account.DEFAULT.copy(id = spendEvent.accountId, amount = initialAmount))
        val requiredSpendEvent = spendEvent.copy(cost = spendCost)

        createEventAndUpdateAccount(
            spendEvent = requiredSpendEvent,
            eventsRepository = eventsRepositoryMock,
            accountsRepository = accountsRepositoryMock
        )

        assertThat(accountsRepositoryMock.stubbedUpdateAmount.firstOrNull()).isEqualTo(requiredAccountAmount)
        assertThat(accountsRepositoryMock.stubbedUpdateId.firstOrNull()).isEqualTo(requiredSpendEvent.accountId)
    }


    @Test
    fun `editExistedEventAndUpdateAccount update spend event in events repository`() {
        val oldEvent = spendEvent.copy(id = "old", accountId = "old", cost = 500.0)
        val oldAccount = Account.DEFAULT.copy(id = "old", amount = 1_000.0)
        val newEvent = spendEvent.copy(id = "new", accountId = "new", cost = 100.0)
        val newAccount = Account.DEFAULT.copy(id = "new", amount = 2_000.0)
        accountsRepositoryMock.enqueueAccounts(oldAccount, newAccount)

        editExistedEventAndUpdateAccount(
            oldEvent = oldEvent,
            newEvent = newEvent,
            eventsRepository = eventsRepositoryMock,
            accountsRepository = accountsRepositoryMock
        )

        assertThat(eventsRepositoryMock.isUpdateInvoked).isTrue()
        assertThat(eventsRepositoryMock.updateId).isEqualTo(newEvent.id)
    }

    @Test
    fun `editExistedEventAndUpdateAccount reset oldAccount`() {
        val oldEvent = spendEvent.copy(id = "old", accountId = "old", cost = 500.0)
        val oldAccount = Account.DEFAULT.copy(id = "old", amount = 1_000.0)
        val newEvent = spendEvent.copy(id = "new", accountId = "new", cost = 100.0)
        val newAccount = Account.DEFAULT.copy(id = "new", amount = 2_000.0)
        accountsRepositoryMock.enqueueAccounts(oldAccount, newAccount)

        editExistedEventAndUpdateAccount(
            oldEvent = oldEvent,
            newEvent = newEvent,
            eventsRepository = eventsRepositoryMock,
            accountsRepository = accountsRepositoryMock
        )

        val requiredAccount = oldAccount.copy(amount = oldAccount.amount + oldEvent.cost)
        assertThat(accountsRepositoryMock.stubbedUpdateId.firstOrNull()).isEqualTo(requiredAccount.id)
        assertThat(accountsRepositoryMock.stubbedUpdateAmount.firstOrNull()).isEqualTo(requiredAccount.amount)
    }

    @Test
    fun `editExistedEventAndUpdateAccount update newAccount`() {
        val oldEvent = spendEvent.copy(id = "old", accountId = "old", cost = 500.0)
        val oldAccount = Account.DEFAULT.copy(id = "old", amount = 1_000.0)
        val newEvent = spendEvent.copy(id = "new", accountId = "new", cost = 100.0)
        val newAccount = Account.DEFAULT.copy(id = "new", amount = 2_000.0)
        accountsRepositoryMock.enqueueAccounts(oldAccount, newAccount)

        editExistedEventAndUpdateAccount(
            oldEvent = oldEvent,
            newEvent = newEvent,
            eventsRepository = eventsRepositoryMock,
            accountsRepository = accountsRepositoryMock
        )

        val requiredAccount = newAccount.copy(amount = newAccount.amount - newEvent.cost)
        assertThat(accountsRepositoryMock.stubbedUpdateId.lastOrNull()).isEqualTo(requiredAccount.id)
        assertThat(accountsRepositoryMock.stubbedUpdateAmount.lastOrNull()).isEqualTo(requiredAccount.amount)
    }
}