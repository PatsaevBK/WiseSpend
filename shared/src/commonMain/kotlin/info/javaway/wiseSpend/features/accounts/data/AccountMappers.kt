package info.javaway.wiseSpend.features.accounts.data

import db.accounts.AccountTable
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.accounts.models.AccountApi
import info.javaway.wiseSpend.features.accounts.models.AccountUi
import info.javaway.wiseSpend.features.events.list.EventsScreenContract.State.Companion.TOTAL_ACCOUNTS_NAME

fun AccountApi.toDb() = AccountTable(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt
)

fun AccountTable.toAccount() = Account(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt, currency = Account.DEFAULT_CURRENCY,
)

fun Account.toApi() = AccountApi(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt
)

fun Account.toDb() = AccountTable(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt
)

fun Account.toUi() = AccountUi(
    id = id, name = name, formattedAmount = "$amount $currency",
)

fun buildAccountsUi(accounts: List<Account>): List<AccountUi> {
    val totals = accounts.fold(0.0) { total, account ->
        total + account.amount
    }
    val currency = accounts.firstOrNull()?.currency
    val totalAccount =
        AccountUi(id = null, TOTAL_ACCOUNTS_NAME, "$totals ${currency.orEmpty()}")
    return buildList {
        add(totalAccount)
        addAll(accounts.map { it.toUi() })
    }
}