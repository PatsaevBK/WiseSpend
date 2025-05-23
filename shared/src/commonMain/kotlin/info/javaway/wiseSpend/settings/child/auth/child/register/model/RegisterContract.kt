package info.javaway.wiseSpend.settings.child.auth.child.register.model

import info.javaway.wiseSpend.base.BaseViewEvent
import info.javaway.wiseSpend.base.BaseViewState

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

    sealed interface Effect : BaseViewEvent {
        data class Error(val message: String) : Effect
    }
}