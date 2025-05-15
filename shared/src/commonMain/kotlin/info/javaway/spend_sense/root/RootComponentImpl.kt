package info.javaway.spend_sense.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import info.javaway.spend_sense.categories.CategoriesComponentImpl
import info.javaway.spend_sense.events.EventsComponentImpl
import info.javaway.spend_sense.settings.SettingsComponentImpl
import kotlinx.serialization.Serializable

class RootComponentImpl(
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext {

    private val nav = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = nav,
        serializer = Config.serializer(),
        initialConfiguration = Config.Events,
        key = "RootStack",
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(config: Config, componentContext: ComponentContext) : RootComponent.Child =
        when (config) {
            Config.Categories -> RootComponent.Child.Categories(component = CategoriesComponentImpl(componentContext))
            Config.Events -> RootComponent.Child.Events(component = EventsComponentImpl(componentContext))
            Config.Settings -> RootComponent.Child.Settings(component = SettingsComponentImpl(componentContext))
        }

    @Serializable
    private sealed interface Config {
        data object Events : Config
        data object Categories : Config
        data object Settings : Config
    }
}