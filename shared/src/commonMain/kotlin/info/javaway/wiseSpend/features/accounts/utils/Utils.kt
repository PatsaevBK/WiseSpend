package info.javaway.wiseSpend.features.accounts.utils

import info.javaway.wiseSpend.features.accounts.data.AccountRepository
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.events.data.EventsRepository
import info.javaway.wiseSpend.features.events.models.SpendEvent

suspend fun createEventAndUpdateAccount(
    spendEvent: SpendEvent,
    eventsRepository: EventsRepository,
    accountsRepository: AccountRepository
) {
    eventsRepository.create(spendEvent)
    val oldAccount = accountsRepository.getById(spendEvent.accountId) ?: return

    val decreasedAmount = oldAccount.amount - spendEvent.cost
    updateAccount(oldAccount, decreasedAmount, accountsRepository)
}

suspend fun editExistedEventAndUpdateAccount(
    oldEvent: SpendEvent,
    newEvent: SpendEvent,
    eventsRepository: EventsRepository,
    accountsRepository: AccountRepository
) {
    val oldAccount = accountsRepository.getById(oldEvent.accountId) ?: return
    val newAccount = accountsRepository.getById(newEvent.accountId) ?: return

    with(newEvent) {
        eventsRepository.update(
            categoryId = categoryId,
            accountId = accountId,
            title = title,
            cost = cost,
            date = date,
            updatedAt = updatedAt,
            note = note,
            id = id
        )
    }

    val accountTheSame = oldAccount.id == newAccount.id

    when {
        accountTheSame.not() -> {
            resetOldAccount(
                oldAccount = oldAccount,
                oldEvent = oldEvent,
                accountsRepository = accountsRepository
            )

            val decreasedAmount = newAccount.amount - newEvent.cost
            updateAccount(
                newAccount = newAccount,
                newAmount = decreasedAmount,
                accountsRepository = accountsRepository
            )
        }

        accountTheSame && oldEvent.cost != newEvent.cost -> {
            if (oldEvent.cost > newEvent.cost) {
                val increasedAmount = oldAccount.amount + (oldEvent.cost - newEvent.cost)
                updateAccount(
                    newAccount = oldAccount,
                    newAmount = increasedAmount,
                    accountsRepository = accountsRepository
                )
            } else {
                val decreasedAmount = oldAccount.amount - (newEvent.cost - oldEvent.cost)
                updateAccount(
                    newAccount = oldAccount,
                    newAmount = decreasedAmount,
                    accountsRepository = accountsRepository
                )
            }
        }
    }
}

private suspend fun updateAccount(
    newAccount: Account,
    newAmount: Double,
    accountsRepository: AccountRepository
) {
    val updateNewAccount = newAccount.copy(amount = newAmount)
    with(updateNewAccount) {
        accountsRepository.update(
            id = id,
            name = name,
            amount = amount,
            updatedAt = updatedAt
        )
    }
}

private suspend fun resetOldAccount(
    oldAccount: Account,
    oldEvent: SpendEvent,
    accountsRepository: AccountRepository
) {
    val resetOldAccount = oldAccount.copy(amount = oldAccount.amount + oldEvent.cost)
    with(resetOldAccount) {
        accountsRepository.update(
            id = id,
            name = name,
            amount = amount,
            updatedAt = updatedAt
        )
    }
}