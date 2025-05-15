package info.javaway.spend_sense.settings.child.auth.child.register

import com.arkivanov.decompose.ComponentContext
import info.javaway.spend_sense.events.extensions.componentScope
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.network.ApiErrorWrapper
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.settings.child.auth.child.register.model.AuthResponse
import info.javaway.spend_sense.settings.child.auth.child.register.model.RegisterContract
import info.javaway.spend_sense.settings.child.auth.child.register.model.RegisterRequest
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterComponentImpl(
    componentContext: ComponentContext,
    private val api: AppApi,
    private val settings: SettingsManager,
    private val onOutput: (RegisterComponent.Output) -> Unit,
) : RegisterComponent, ComponentContext by componentContext {

    private val scope = componentScope()

    private val _model = MutableStateFlow(RegisterContract.State.NONE)
    override val model: StateFlow<RegisterContract.State> = _model.asStateFlow()

    private val _effects = MutableSharedFlow<RegisterContract.Effect>()
    override val effects: SharedFlow<RegisterContract.Effect> = _effects.asSharedFlow()

    override fun changeEmail(email: String) {
        _model.update { it.copy(email = email) }
    }

    override fun changePassword(pass: String) {
        _model.update { it.copy(password = pass) }
    }

    override fun changeConfirmPassword(pass: String) {
        _model.update { it.copy(confirmPassword = pass) }
    }

    override fun register() {
        val registerReq = RegisterRequest(
            email = model.value.email,
            password = model.value.password,
            username = model.value.email
        )

        scope.launch(
            CoroutineExceptionHandler { _, t ->
                appLog(t.stackTraceToString())
                _effects.tryEmit(RegisterContract.Effect.Error(t.message.orEmpty()))
            }
        ) {
            val response = api.register(registerReq)
            if (response.status.isSuccess()) {
                val regResponse = response.body<AuthResponse>()
                settings.token = regResponse.jwt.orEmpty()
                settings.email = model.value.email
                withContext(Dispatchers.Main) {
                    onOutput.invoke(RegisterComponent.Output.Success)
                }
            } else {
                val error = response.body<ApiErrorWrapper>().error
                _effects.emit(RegisterContract.Effect.Error(error?.message ?: response.bodyAsText()))
            }
        }
    }

    override fun dismiss() {
        onOutput.invoke(RegisterComponent.Output.Dismiss)
    }

    class Factory(
        private val api: AppApi,
        private val settings: SettingsManager,
    ): RegisterComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onOutput: (RegisterComponent.Output) -> Unit
        ): RegisterComponent = RegisterComponentImpl(
            componentContext = componentContext,
            api = api,
            settings = settings,
            onOutput = onOutput
        )
    }
}