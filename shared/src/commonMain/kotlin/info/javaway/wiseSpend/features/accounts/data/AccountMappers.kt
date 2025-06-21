package info.javaway.wiseSpend.features.accounts.data

import db.accounts.AccountTable
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.accounts.models.AccountApi

fun AccountApi.toDb() = AccountTable(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt
)

fun AccountTable.toAccount() = Account(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt
)

fun Account.toApi() = AccountApi(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt
)

fun Account.toDb() = AccountTable(
    id = id, name = name, amount = amount, createdAt = createdAt, updatedAt = updatedAt
)