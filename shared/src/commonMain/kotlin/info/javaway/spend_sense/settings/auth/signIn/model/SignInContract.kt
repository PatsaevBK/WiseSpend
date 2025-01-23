package info.javaway.spend_sense.settings.auth.signIn.model

import info.javaway.spend_sense.base.BaseViewEvent
import info.javaway.spend_sense.base.BaseViewState

interface SignInContract {

    data class State(
        val email: String,
        val password: String
    ) : BaseViewState {
        companion object {
            val NONE = State(
                email = "",
                password = ""
            )
        }
    }

    sealed interface Event : BaseViewEvent {
        data object Success : Event
        data class Error(val message: String) : Event
    }
}