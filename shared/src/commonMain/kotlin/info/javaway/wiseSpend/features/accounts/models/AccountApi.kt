package info.javaway.wiseSpend.features.accounts.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountApi(
    @SerialName("idLocal")
    val id: String,
    val name: String,
    val amount: Double,
    @Contextual
    @SerialName("createdAtLocal")
    val createdAt: LocalDateTime,
    @Contextual
    @SerialName("updatedAtLocal")
    val updatedAt: LocalDateTime,
)