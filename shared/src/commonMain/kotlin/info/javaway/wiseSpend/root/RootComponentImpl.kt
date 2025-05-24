package info.javaway.wiseSpend.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponent
import info.javaway.wiseSpend.extensions.componentScope
import info.javaway.wiseSpend.features.events.list.EventsListComponent
import info.javaway.wiseSpend.root.model.RootContract
import info.javaway.wiseSpend.features.settings.SettingsComponent
import info.javaway.wiseSpend.storage.SettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable

class RootComponentImpl(
    componentContext: ComponentContext,
    settingsManager: SettingsManager,
    private val categoriesFactory: CategoriesListComponent.Factory,
    private val eventsListComponentFactory: EventsListComponent.Factory,
    private val settingsComponent: SettingsComponent.Factory,
): RootComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val nav = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = nav,
        serializer = Config.serializer(),
        initialConfiguration = Config.Events,
        key = "RootStack",
        handleBackButton = true,
        childFactory = ::child
    )

    private val _state = MutableStateFlow(RootContract.State.NONE)
    override val state: StateFlow<RootContract.State> = _state.asStateFlow()

    init {
        settingsManager.themeIsDarkFlow.onEach {
            _state.update { oldState -> oldState.copy(themeIsDark = it) }
        }.launchIn(scope = scope)
    }

    override fun onEventsClick() {
        nav.bringToFront(Config.Events)
    }

    override fun onCategoriesClick() {
        nav.bringToFront(Config.Categories)
    }

    override fun onSettingsClick() {
        nav.bringToFront(Config.Settings)
    }

    private fun child(config: Config, componentContext: ComponentContext) : RootComponent.Child =
        when (config) {
            Config.Categories ->
                RootComponent.Child.Categories(component = categoriesFactory.create(componentContext))
            Config.Events ->
                RootComponent.Child.Events(component = eventsListComponentFactory.create(componentContext))
            Config.Settings ->
                RootComponent.Child.Settings(component = settingsComponent.create(componentContext))
        }

    @Serializable
    private sealed interface Config {
        data object Events : Config
        data object Categories : Config
        data object Settings : Config
    }

    class Factory(
        private val categoriesFactory: CategoriesListComponent.Factory,
        private val eventsListComponentFactory: EventsListComponent.Factory,
        private val settingsComponent: SettingsComponent.Factory,
        private val settingsManager: SettingsManager,
    ): RootComponent.Factory {
        override fun create(componentContext: ComponentContext): RootComponent {
            return RootComponentImpl(
                componentContext = componentContext,
                settingsManager = settingsManager,
                categoriesFactory = categoriesFactory,
                eventsListComponentFactory = eventsListComponentFactory,
                settingsComponent = settingsComponent
            )
        }
    }
}