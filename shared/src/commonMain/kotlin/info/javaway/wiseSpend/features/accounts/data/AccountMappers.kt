package info.javaway.wiseSpend.features.accounts.data

import db.accounts.AccountTable
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.accounts.models.AccountApi
import info.javaway.wiseSpend.features.accounts.models.AccountUi
import info.javaway.wiseSpend.features.accounts.models.Amount
import info.javaway.wiseSpend.features.events.list.EventsScreenContract.State.Companion.TOTAL_ACCOUNTS_NAME

fun AccountApi.toAccount() = Account(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt, currency = currency
)

fun AccountTable.toAccount() = Account(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt, currency = Account.DEFAULT_CURRENCY,
)

fun Account.toApi() = AccountApi(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt, currency = currency
)

fun Account.toAccountEntity() = AccountTable(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt
)

fun Account.toUi() = AccountUi(
    id = id, name = name, amount = amount.toAmount(currency),
)

fun buildAccountsUiWithTotals(accounts: List<Account>): List<AccountUi> {
    val totals = accounts.fold(0.0) { total, account ->
        total + account.amount
    }
    val currency = accounts.firstOrNull()?.currency
    val totalAccount =
        AccountUi(id = null, TOTAL_ACCOUNTS_NAME, amount = totals.toAmount(currency.orEmpty()))
    return buildList {
        add(totalAccount)
        addAll(accounts.map { it.toUi() }.sortedBy { it.name })
    }
}

fun findSelectedAccount(accounts: List<Account>, selectedAccountId: String?): AccountUi {
    val selectedAccount =
        accounts.firstOrNull { account -> account.id == selectedAccountId }

    selectedAccount ?: run {
        val totals = accounts.fold(0.0) { total, account ->
            total + account.amount
        }
        val currency = accounts.firstOrNull()?.currency
        return AccountUi(id = null, TOTAL_ACCOUNTS_NAME,  totals.toAmount(currency.orEmpty()))
    }
    return AccountUi(
        id = selectedAccount.id,
        name = selectedAccount.name,
        amount = selectedAccount.amount.toAmount(selectedAccount.currency)
    )
}

private fun Double.toAmount(currency: String): Amount {
    val integerPart = this.toInt()
    val fractional = this % 1
    val floatPart = kotlin.math.round(fractional * 100).toInt()
    return Amount(
        integerPart = integerPart,
        floatPart = floatPart.toString(),
        currency = currency
    )
}