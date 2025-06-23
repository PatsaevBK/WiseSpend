package info.javaway.wiseSpend.features.accounts.list

import com.arkivanov.decompose.ComponentContext
import info.javaway.wiseSpend.extensions.componentScope
import info.javaway.wiseSpend.features.accounts.data.AccountRepository

class AccountsListComponentImpl(
    componentContext: ComponentContext,
    private val accountRepository: AccountRepository,
): AccountsListComponent, ComponentContext by componentContext {

    private val scope = componentScope()

    class Factory(
        private val accountRepository: AccountRepository,
    ) : AccountsListComponent.Factory {
        override fun create(componentContext: ComponentContext): AccountsListComponent {
            return AccountsListComponentImpl(componentContext, accountRepository)
        }
    }
}