package info.javaway.wiseSpend.features.settings.child.auth.child.register.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
)




