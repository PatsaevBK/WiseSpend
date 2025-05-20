package info.javaway.wiseSpend.settings.child.auth.child.signIn.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val identifier: String,
    val password: String,
)
