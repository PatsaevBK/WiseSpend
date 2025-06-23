package info.javaway.wiseSpend.features.accounts.creation

import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.accounts.models.Account
import info.javaway.wiseSpend.features.accounts.models.Account.Companion.DEFAULT_CURRENCY
import info.javaway.wiseSpend.platform.randomUUID
import kotlinx.datetime.LocalDateTime

interface CreateAccountContract {
    data class State(
        val account: Account,
    ) : BaseViewState {

        val isValidToSave
            get() = account.name.isNotEmpty() && (account.amount >= 0)

        companion object {
            val NONE = State(
                account = Account(
                    id = randomUUID(),
                    name = "",
                    amount = 0.0,
                    currency = DEFAULT_CURRENCY,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )
            )
        }
    }
}