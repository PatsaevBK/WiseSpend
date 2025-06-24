package info.javaway.wiseSpend.features.accounts.data

import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

class AccountRepository(
    private val dao: AccountDao,
) {

    init {
        insertDefaultAccountIfNeed()
    }

    fun create(account: Account) =
        dao.insert(account)

    fun getById(accountId: String): Account? =
        dao.get(accountId)

    fun getAllFlow(): Flow<List<Account>> =
        dao.getAllFlow()

    fun getAll(): List<Account> = dao.getAll()

    fun update(id: String, name: String, amount: Double, updatedAt: LocalDateTime) =
        dao.update(id = id, name = name, amount = amount, updatedAt = updatedAt)

    private fun insertDefaultAccountIfNeed() {
        if (getById(Account.DEFAULT_ID) == null) {
            create(Account.DEFAULT)
        }
    }
}