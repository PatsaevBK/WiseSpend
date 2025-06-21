package info.javaway.wiseSpend.features.accounts.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import info.javaway.wiseSpend.db.AppDb
import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class AccountDao(
    db: AppDb,
    private val coroutineContext: CoroutineContext,
) {

    private val accountTableQueries = db.accountTableQueries

    fun getAll(): List<Account> = accountTableQueries
        .getAll()
        .executeAsList()
        .map { it.toAccount() }

    fun getAllFlow(): Flow<List<Account>> = accountTableQueries
        .getAll()
        .asFlow()
        .mapToList(coroutineContext)
        .map { accounts -> accounts.map { it.toAccount() } }

    fun get(id: String) = accountTableQueries
        .get(id)
        .executeAsOneOrNull()
        ?.toAccount()

    suspend fun insert(account: Account) = accountTableQueries
        .insert(account.toDb())

    suspend fun insertAll(categories: List<Account>) = accountTableQueries
        .transaction {
            categories.forEach { accountTableQueries.insert(it.toDb()) }
        }


    suspend fun delete(id: String) = accountTableQueries
        .delete(id)
}