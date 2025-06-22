package info.javaway.wiseSpend.features.accounts.data

import db.accounts.AccountTable
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.accounts.models.AccountApi
import info.javaway.wiseSpend.features.accounts.models.AccountUi

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
    id = id, name = name, formattedAmount = "$amount $currency"
)