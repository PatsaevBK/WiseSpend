package info.javaway.wiseSpend.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.categories.list.CategoriesListComponent
import info.javaway.wiseSpend.events.list.EventsListComponent
import info.javaway.wiseSpend.root.model.RootContract
import info.javaway.wiseSpend.settings.SettingsComponent
import kotlinx.coroutines.flow.StateFlow

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>
    val state: StateFlow<RootContract.State>

    fun onEventsClick()
    fun onCategoriesClick()
    fun onSettingsClick()

    sealed interface Child {
        data class Events(val component: EventsListComponent) : Child
        data class Categories(val component: CategoriesListComponent) : Child
        data class Settings(val component: SettingsComponent) : Child
    }

    interface Factory {
        fun create(componentContext: ComponentContext): RootComponent
    }
}