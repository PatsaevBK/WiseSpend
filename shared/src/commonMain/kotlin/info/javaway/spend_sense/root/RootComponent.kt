package info.javaway.spend_sense.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import info.javaway.spend_sense.categories.CategoriesComponent
import info.javaway.spend_sense.events.EventsComponent
import info.javaway.spend_sense.settings.SettingsComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class Events(val component: EventsComponent) : Child
        data class Categories(val component: CategoriesComponent) : Child
        data class Settings(val component: SettingsComponent) : Child
    }
}