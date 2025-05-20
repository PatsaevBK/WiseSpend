package info.javaway.wiseSpend.settings.child.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.settings.child.auth.child.register.RegisterComponent
import info.javaway.wiseSpend.settings.child.auth.child.signIn.SignInComponent

interface AuthComponent {

    val slots: Value<ChildSlot<*, Child>>

    fun onClickOnSignIn()
    fun onClickOnRegister()

    sealed interface Output {
        data object Success : Output
    }

    interface Factory {
        fun create(componentContext: ComponentContext, onOutput: (Output) -> Unit): AuthComponent
    }

    sealed interface Child {
        data class Register(val component: RegisterComponent) : Child
        data class SignIn(val component: SignInComponent) : Child
    }
}