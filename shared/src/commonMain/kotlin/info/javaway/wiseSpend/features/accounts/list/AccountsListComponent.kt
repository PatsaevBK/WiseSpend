package info.javaway.wiseSpend.features.accounts.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.features.accounts.creation.CreateAccountComponent
import kotlinx.coroutines.flow.StateFlow

interface AccountsListComponent {

    val model: StateFlow<AccountsListContract.State>
    val slot: Value<ChildSlot<*, CreateAccountComponent>>

    fun changeAccount(accountId: String)
    fun newAccount()
    fun onDismiss()

    interface Factory {
        fun create(componentContext: ComponentContext) : AccountsListComponent
    }
}