package info.javaway.wiseSpend.settings.child.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.events.extensions.componentScope
import info.javaway.wiseSpend.settings.child.auth.child.register.RegisterComponent
import info.javaway.wiseSpend.settings.child.auth.child.signIn.SignInComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class AuthComponentImpl(
    componentContext: ComponentContext,
    private val registerComponentFactory: RegisterComponent.Factory,
    private val signInComponentFactory: SignInComponent.Factory,
    private val onOutput: (AuthComponent.Output) -> Unit,
) : AuthComponent, ComponentContext by componentContext {

    private val scope = componentScope()

    private val nav = SlotNavigation<Config>()
    override val slots: Value<ChildSlot<*, AuthComponent.Child>> = childSlot(
        source = nav,
        serializer = Config.serializer(),
        childFactory = ::child,
    )

    override fun onClickOnSignIn() {
        nav.activate(Config.SignIn)
    }

    override fun onClickOnRegister() {
        nav.activate(Config.Register)
    }

    private fun child(config: Config, componentContext: ComponentContext): AuthComponent.Child {
        return when (config) {
            Config.Register -> AuthComponent.Child.Register(
                registerComponentFactory.create(
                    componentContext = componentContext,
                    onOutput = ::onRegisterOutput
                )
            )

            Config.SignIn -> AuthComponent.Child.SignIn(
                signInComponentFactory.create(
                    componentContext = componentContext,
                    onOutput = ::onSignInOutput
                )
            )
        }
    }

    private fun onSignInOutput(output: SignInComponent.Output) {
        when (output) {
            SignInComponent.Output.Dismiss -> nav.dismiss()
            SignInComponent.Output.Success -> completeLogin()
        }
    }

    private fun onRegisterOutput(output: RegisterComponent.Output) {
        when (output) {
            is RegisterComponent.Output.Success -> completeLogin()
            is RegisterComponent.Output.Dismiss -> nav.dismiss()
        }
    }

    private fun completeLogin() {
        scope.launch(Dispatchers.Main) {
            nav.dismiss()
            onOutput(AuthComponent.Output.Success)
        }
    }

    @Serializable
    private sealed interface Config {
        data object Register : Config
        data object SignIn : Config
    }

    class Factory(
        private val registerComponentFactory: RegisterComponent.Factory,
        private val signInComponentFactory: SignInComponent.Factory,
    ): AuthComponent.Factory {

        override fun create(
            componentContext: ComponentContext,
            onOutput: (AuthComponent.Output) -> Unit
        ): AuthComponent = AuthComponentImpl(
            componentContext = componentContext,
            registerComponentFactory = registerComponentFactory,
            signInComponentFactory = signInComponentFactory,
            onOutput = onOutput
        )
    }
}