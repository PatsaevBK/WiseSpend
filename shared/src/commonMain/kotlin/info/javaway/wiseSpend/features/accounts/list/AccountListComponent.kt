package info.javaway.wiseSpend.features.accounts.list

import com.arkivanov.decompose.ComponentContext
import info.javaway.wiseSpend.extensions.componentScope
import info.javaway.wiseSpend.features.accounts.data.AccountRepository

class AccountListComponent(
    componentContext: ComponentContext,
    private val accountRepository: AccountRepository,
): ComponentContext by componentContext {

    private val scope = componentScope()

}