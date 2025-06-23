package info.javaway.wiseSpend.features.accounts.data

import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AccountRepository(
    private val dao: AccountDao,
) {

    suspend fun create(account: Account) =
        withContext(Dispatchers.IO) {
            dao.insert(account)
        }

    fun getById(accountId: String): Account? =
        dao.get(accountId)

    fun getAllFlow(): Flow<List<Account>> =
        dao.getAllFlow()

    fun getAll(): List<Account> = dao.getAll()
}