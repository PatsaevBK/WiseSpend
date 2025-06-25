package info.javaway.wiseSpend.features.accounts.utils

import info.javaway.wiseSpend.features.accounts.data.AccountRepository
import info.javaway.wiseSpend.features.events.data.EventsRepository
import info.javaway.wiseSpend.features.events.models.SpendEvent

fun createEventAndUpdateAccount(
    spendEvent: SpendEvent,
    eventsRepository: EventsRepository,
    accountsRepository: AccountRepository
) {
    eventsRepository.create(spendEvent)
    val oldAccount = accountsRepository.getById(spendEvent.accountId) ?: return
    val updatedAccount = oldAccount.copy(amount = oldAccount.amount - spendEvent.cost)
    with(updatedAccount) {
        accountsRepository.update(id = id, name = name, amount = amount, updatedAt = updatedAt)
    }
}

fun editExistedEventAndUpdateAccount(
    oldEvent: SpendEvent,
    newEvent: SpendEvent,
    eventsRepository: EventsRepository,
    accountsRepository: AccountRepository
) {
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
    val oldAccount = accountsRepository.getById(oldEvent.accountId) ?: return
    val newAccount = accountsRepository.getById(newEvent.accountId) ?: return

    val costUnchanged = oldEvent.cost == newEvent.cost
    val sameAccount = oldAccount.id == newAccount.id

    if (costUnchanged && sameAccount) return

    if (!sameAccount) {
        val resetOldAccount = oldAccount.copy(amount = oldAccount.amount + oldEvent.cost)
        with(resetOldAccount) {
            accountsRepository.update(id = id, name = name, amount = amount, updatedAt = updatedAt)
        }
    }

    val amountDelta = newAccount.amount - newEvent.cost
    if (amountDelta != 0.0) {
        val updateNewAccount = newAccount.copy(amount = amountDelta)
        with(updateNewAccount) {
            accountsRepository.update(
                id = id,
                name = name,
                amount = amountDelta,
                updatedAt = updatedAt
            )
        }
    }
}