package info.javaway.spend_sense.settings.child.auth.child.signIn

import com.arkivanov.decompose.ComponentContext
import info.javaway.spend_sense.events.extensions.componentScope
import info.javaway.spend_sense.network.ApiErrorWrapper
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.settings.child.auth.child.register.model.AuthResponse
import info.javaway.spend_sense.settings.child.auth.child.signIn.SignInComponent.Output
import info.javaway.spend_sense.settings.child.auth.child.signIn.model.SignInContract
import info.javaway.spend_sense.settings.child.auth.child.signIn.model.SignInContract.Effect
import info.javaway.spend_sense.settings.child.auth.child.signIn.model.SignInRequest
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInComponentImpl(
    componentContext: ComponentContext,
    private val api: AppApi,
    private val settings: SettingsManager,
    private val onOutput: (Output) -> Unit,
) : SignInComponent, ComponentContext by componentContext {

    private val _model = MutableStateFlow(SignInContract.State.NONE)
    override val model: StateFlow<SignInContract.State> = _model.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>()
    override val effects = _effects.asSharedFlow()

    private val scope = componentScope()

    override fun changeEmail(email: String) {
        _model.update { it.copy(email = email) }
    }

    override fun changePassword(pass: String) {
        _model.update { it.copy(password = pass) }
    }

    override fun onDismiss() = onOutput.invoke(Output.Dismiss)

    override fun login() {
        val signInReq = SignInRequest(
            identifier = _model.value.email,
            password = _model.value.password
        )
        scope.launch(
            CoroutineExceptionHandler { _, t -> handleError(t) }
        ) {
            val response = api.signIn(signInReq)
            if (response.status.isSuccess()) {
                val signInResponse = response.body<AuthResponse>()
                settings.token = signInResponse.jwt.orEmpty()
                settings.email = _model.value.email
                onOutput(Output.Success)
            } else {
                val error = response.body<ApiErrorWrapper>().error
                _effects.emit(Effect.Error(error?.message ?: response.bodyAsText()))
            }
        }
    }

    private fun handleError(error: Throwable) {
        scope.launch {
            _effects.emit(Effect.Error(error.message.orEmpty()))
        }
    }

    class Factory(
        private val api: AppApi,
        private val settings: SettingsManager,
    ): SignInComponent.Factory {

        override fun create(
            componentContext: ComponentContext,
            onOutput: (Output) -> Unit
        ): SignInComponent = SignInComponentImpl(
            componentContext = componentContext,
            api = api,
            settings = settings,
            onOutput = onOutput
        )
    }
}