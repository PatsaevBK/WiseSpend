package info.javaway.wiseSpend.features.accounts.list

import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.features.accounts.data.buildAccountsUiWithTotals
import info.javaway.wiseSpend.features.accounts.data.toUi
import info.javaway.wiseSpend.features.accounts.models.Account

interface AccountsListContract {

    data class State(
        val accounts: List<Account>
    ) : BaseViewState {
        val accountsUIWithTotals
            get() = buildAccountsUiWithTotals(accounts)

        val accountsUI
            get() = accounts.map { it.toUi() }
    }
}