package info.javaway.wiseSpend.settings.child.auth.child.signIn.model

import info.javaway.wiseSpend.base.BaseViewState

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

    sealed interface Effect {
        data class Error(val message: String) : Effect
    }
}