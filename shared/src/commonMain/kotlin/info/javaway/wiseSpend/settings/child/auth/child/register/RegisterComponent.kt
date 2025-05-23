package info.javaway.wiseSpend.settings.child.auth.child.register

import com.arkivanov.decompose.ComponentContext
import info.javaway.wiseSpend.settings.child.auth.child.register.model.RegisterContract
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface RegisterComponent {

    val model: StateFlow<RegisterContract.State>
    val effects: SharedFlow<RegisterContract.Effect>

    fun changeEmail(email: String)
    fun changePassword(pass: String)
    fun changeConfirmPassword(pass: String)
    fun register()
    fun dismiss()

    sealed interface Output {
        data object Success : Output
        data object Dismiss : Output
    }

    interface Factory {
        fun create(componentContext: ComponentContext, onOutput: (Output) -> Unit) : RegisterComponent
    }
}