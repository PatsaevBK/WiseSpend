package info.javaway.wiseSpend.mocks

import info.javaway.wiseSpend.features.accounts.data.AccountRepository
import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

internal class AccountsRepositoryMock: AccountRepository {

    var createInvoked = false
    override suspend fun create(account: Account) {
        createInvoked = true
    }

    private val accountsQueue = ArrayDeque<Account>()
    // Добавить аккаунты, которые должны возвращаться по порядку
    fun enqueueAccounts(vararg accounts: Account) {
        accountsQueue.addAll(accounts)
    }
    var getByIdInvoked = false
    var getByIdInvokedWithId = ""
    override suspend fun getById(accountId: String): Account? {
        getByIdInvokedWithId = accountId
        getByIdInvoked = true
        return accountsQueue.removeFirstOrNull()
    }

    override fun getAllFlow(): Flow<List<Account>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Account> {
        TODO("Not yet implemented")
    }

    val stubbedUpdateId = mutableListOf<String>()
    val stubbedUpdateAmount = mutableListOf<Double>()
    override suspend fun update(
        id: String,
        name: String,
        amount: Double,
        updatedAt: LocalDateTime
    ) {
        stubbedUpdateAmount.add(amount)
        stubbedUpdateId.add(id)
    }

    override suspend fun insertAll(accounts: List<Account>) {
        TODO("Not yet implemented")
    }
}