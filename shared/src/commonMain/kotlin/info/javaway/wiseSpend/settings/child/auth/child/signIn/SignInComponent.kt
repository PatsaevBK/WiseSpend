package info.javaway.wiseSpend.settings.child.auth.child.signIn

import com.arkivanov.decompose.ComponentContext
import info.javaway.wiseSpend.settings.child.auth.child.signIn.model.SignInContract
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SignInComponent {

    val model: StateFlow<SignInContract.State>
    val effects: SharedFlow<SignInContract.Effect>

    fun changeEmail(email: String)
    fun changePassword(pass: String)
    fun onDismiss()
    fun login()

    sealed interface Output {
        data object Success : Output
        data object Dismiss : Output
    }

    interface Factory {
        fun create(componentContext: ComponentContext, onOutput: (Output) -> Unit): SignInComponent
    }
}