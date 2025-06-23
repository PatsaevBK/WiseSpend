package info.javaway.wiseSpend.features.accounts.list

import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.features.accounts.data.buildAccountsUi
import info.javaway.wiseSpend.features.accounts.models.Account

interface AccountsListContract {

    data class State(
        val accounts: List<Account>
    ) : BaseViewState {
        val accountsUI
            get() = buildAccountsUi(accounts)
    }

}