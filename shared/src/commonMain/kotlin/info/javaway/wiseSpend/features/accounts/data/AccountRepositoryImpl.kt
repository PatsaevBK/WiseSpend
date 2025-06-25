package info.javaway.wiseSpend.features.accounts.data

import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

class AccountRepositoryImpl(
    private val dao: AccountDao,
): AccountRepository {

    init {
        insertDefaultAccountIfNeed()
    }

    override fun create(account: Account) =
        dao.insert(account)

    override fun getById(accountId: String): Account? =
        dao.get(accountId)

    override fun getAllFlow(): Flow<List<Account>> =
        dao.getAllFlow()

    override fun getAll(): List<Account> = dao.getAll()

    override fun update(id: String, name: String, amount: Double, updatedAt: LocalDateTime) =
        dao.update(id = id, name = name, amount = amount, updatedAt = updatedAt)

    override fun insertAll(accounts: List<Account>) = dao.insertAll(accounts)

    private fun insertDefaultAccountIfNeed() {
        if (getById(Account.DEFAULT_ID) == null) {
            create(Account.DEFAULT)
        }
    }
}