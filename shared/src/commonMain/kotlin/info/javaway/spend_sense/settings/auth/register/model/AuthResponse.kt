package info.javaway.spend_sense.settings.auth.register.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val jwt: String?
)
