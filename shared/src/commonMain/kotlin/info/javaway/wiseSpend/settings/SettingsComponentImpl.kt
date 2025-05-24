package info.javaway.wiseSpend.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.extensions.componentScope
import info.javaway.wiseSpend.platform.DeviceInfo
import info.javaway.wiseSpend.settings.SettingsContract.Effect
import info.javaway.wiseSpend.settings.child.auth.AuthComponent
import info.javaway.wiseSpend.settings.child.sync.SyncComponent
import info.javaway.wiseSpend.storage.SettingsManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable

class SettingsComponentImpl(
    componentContext: ComponentContext,
    private val deviceInfo: DeviceInfo,
    private val settingsManager: SettingsManager,
    private val authComponentFactory: AuthComponent.Factory,
    private val syncComponentFactory: SyncComponent.Factory,
) : SettingsComponent, ComponentContext by componentContext {

    private val scope = componentScope()

    private val _model = MutableStateFlow(SettingsContract.State.NONE)
    override val model: StateFlow<SettingsContract.State> = _model.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>()
    override val effects: SharedFlow<Effect> = _effects.asSharedFlow()

    private val nav = StackNavigation<Config>()

    private val initConfig: Config
        get() = if(settingsManager.email.isBlank()) Config.Auth else Config.Sync

    override val stack: Value<ChildStack<*, SettingsComponent.Child>> = childStack(
        source = nav,
        serializer = Config.serializer(),
        initialConfiguration = initConfig,
        key = "SettingStack",
        handleBackButton = true,
        childFactory = ::child
    )


    init {
        bindToTheme()
        bindToEmail()
        bindToToken()
        _model.update { it.copy(deviceInfo = deviceInfo.getSummary()) }
    }

    override fun switchTheme(isDark: Boolean) {
        settingsManager.themeIsDark = isDark
    }

    private fun child(config: Config, componentContext: ComponentContext): SettingsComponent.Child {
        return when (config) {
            Config.Auth -> SettingsComponent.Child.Auth(
                authComponentFactory.create(
                    componentContext = componentContext,
                    onOutput = ::onAuthOutput,
                )
            )

            Config.Sync -> SettingsComponent.Child.Sync(
                syncComponentFactory.create(
                    componentContext = componentContext,
                    onOutput = ::onSyncOutput
                )
            )
        }
    }

    private fun bindToTheme() {
        settingsManager.themeIsDarkFlow.onEach {
            _model.update  { oldState ->
                oldState.copy(themeIsDark = it)
            }
        }.launchIn(scope)
    }

    private fun onAuthOutput(output: AuthComponent.Output) {
        when (output) {
            AuthComponent.Output.Success -> nav.replaceAll(Config.Sync)
        }
    }

    private fun onSyncOutput(output: SyncComponent.Output) {
        when (output) {
            SyncComponent.Output.DataSynced -> _effects.tryEmit(Effect.DataSynced)
            is SyncComponent.Output.Error -> _effects.tryEmit(Effect.Error(output.string))
        }
    }

    private fun bindToEmail() {
        settingsManager.emailFlow.onEach { email ->
            _model.update { it.copy(email = email) }
        }.launchIn(scope)
    }

    private fun bindToToken() {
        settingsManager.tokenFlow.onEach { token ->
            _model.update { it.copy(isAuth = token.isNotBlank()) }
        }.launchIn(scope)
    }

    @Serializable
    private sealed class Config {
        data object Auth : Config()
        data object Sync : Config()
    }

    class Factory(
        private val settingsManager: SettingsManager,
        private val authComponentFactory: AuthComponent.Factory,
        private val syncComponentFactory: SyncComponent.Factory,
        private val deviceInfo: DeviceInfo,
    ): SettingsComponent.Factory {
        override fun create(componentContext: ComponentContext): SettingsComponent {
            return SettingsComponentImpl(
                componentContext = componentContext,
                deviceInfo = deviceInfo,
                settingsManager = settingsManager,
                authComponentFactory = authComponentFactory,
                syncComponentFactory = syncComponentFactory
            )
        }

    }
}