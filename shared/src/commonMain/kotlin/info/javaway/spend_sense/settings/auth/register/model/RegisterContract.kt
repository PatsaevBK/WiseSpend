package info.javaway.spend_sense.settings.auth.register.model

import info.javaway.spend_sense.base.BaseViewEvent
import info.javaway.spend_sense.base.BaseViewState

interface RegisterContract {
    data class State(
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : BaseViewState {

        val isPasswordValid: Boolean
            get() = password.isNotBlank() && confirmPassword.isNotBlank() && password == confirmPassword

        companion object {
            val NONE = State(
                email = "",
                password = "",
                confirmPassword = ""
            )
        }
    }

    sealed interface Event : BaseViewEvent {
        data object Success : Event
        data class Error(val message: String) : Event
    }
}