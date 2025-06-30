package info.javaway.wiseSpend.features.accounts.data

import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface AccountRepository {

    suspend fun create(account: Account)

    suspend fun getById(accountId: String): Account?

    fun getAllFlow(): Flow<List<Account>>

    suspend fun getAll(): List<Account>

    suspend fun update(id: String, name: String, amount: Double, updatedAt: LocalDateTime)

    suspend fun insertAll(accounts: List<Account>)
}