package info.javaway.spend_sense.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import info.javaway.spend_sense.categories.list.CategoriesListComponent
import info.javaway.spend_sense.events.list.EventsListComponent
import info.javaway.spend_sense.root.model.RootContract
import info.javaway.spend_sense.settings.SettingsComponent
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