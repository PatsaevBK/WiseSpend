package info.javaway.wiseSpend.features.accounts.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import info.javaway.wiseSpend.db.AppDb
import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
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

    fun insert(account: Account) = accountTableQueries
        .insert(account.toAccountEntity())

    fun insertAll(accounts: List<Account>) = accountTableQueries
        .transaction {
            accounts.forEach { accountTableQueries.insert(it.toAccountEntity()) }
        }


    fun delete(id: String) = accountTableQueries
        .delete(id)

    fun update(id: String, name: String, amount: Double, updatedAt: LocalDateTime) = accountTableQueries
        .update(id = id, name = name, amount = amount, updatedAt = updatedAt)

}