package info.javaway.wiseSpend.features.accounts.data

import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime

class AccountRepositoryImpl(
    private val dao: AccountDao,
): AccountRepository {

    override suspend fun create(account: Account) =
        withContext(Dispatchers.IO) {
            dao.insert(account)
        }

    override suspend fun getById(accountId: String): Account? =
        withContext(Dispatchers.IO) {
            dao.get(accountId)
        }

    override fun getAllFlow(): Flow<List<Account>> =
        dao.getAllFlow().flowOn(Dispatchers.IO)

    override suspend fun getAll(): List<Account> = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    override suspend fun update(id: String, name: String, amount: Double, updatedAt: LocalDateTime) =
        withContext(Dispatchers.IO){
            dao.update(id = id, name = name, amount = amount, updatedAt = updatedAt)
        }

    override suspend fun insertAll(accounts: List<Account>) = withContext(Dispatchers.IO){
        dao.insertAll(accounts)
    }
}