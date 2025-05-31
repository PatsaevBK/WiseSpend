package info.javaway.wiseSpend.features.settings.child.auth.child.register.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val jwt: String?
)
