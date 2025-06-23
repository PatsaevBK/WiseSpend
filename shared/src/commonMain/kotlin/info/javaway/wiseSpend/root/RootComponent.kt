package info.javaway.wiseSpend.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.features.accounts.list.AccountsListComponent
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponent
import info.javaway.wiseSpend.features.events.list.EventsListComponent
import info.javaway.wiseSpend.features.settings.SettingsComponent
import info.javaway.wiseSpend.root.model.RootContract
import kotlinx.coroutines.flow.StateFlow

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>
    val state: StateFlow<RootContract.State>

    fun onEventsClick()
    fun onCategoriesClick()
    fun onAccountsClick()
    fun onSettingsClick()

    sealed interface Child {
        data class Events(val component: EventsListComponent) : Child
        data class Categories(val component: CategoriesListComponent) : Child
        data class Accounts(val component: AccountsListComponent) : Child
        data class Settings(val component: SettingsComponent) : Child
    }

    interface Factory {
        fun create(componentContext: ComponentContext): RootComponent
    }
}