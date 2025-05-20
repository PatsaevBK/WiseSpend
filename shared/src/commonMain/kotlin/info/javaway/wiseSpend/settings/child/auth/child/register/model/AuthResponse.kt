package info.javaway.wiseSpend.settings.child.auth.child.register.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val jwt: String?
)
