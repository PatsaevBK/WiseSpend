package info.javaway.wiseSpend.features.accounts.data

import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface AccountRepository {

    fun create(account: Account)

    fun getById(accountId: String): Account?

    fun getAllFlow(): Flow<List<Account>>

    fun getAll(): List<Account>

    fun update(id: String, name: String, amount: Double, updatedAt: LocalDateTime)

    fun insertAll(accounts: List<Account>)
}