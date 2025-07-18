package info.javaway.wiseSpend.features.events.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpendEventApi(
    @SerialName("idLocal")
    val id: String?,
    val title: String?,
    val categoryId: String?,
    val accountId: String?,
    val cost: Double?,
    @Contextual
    val date: LocalDate?,
    val note: String?,
    @Contextual
    @SerialName("createdAtLocal")
    val createdAt: LocalDateTime?,
    @Contextual
    @SerialName("updatedAtLocal")
    val updatedAt: LocalDateTime?,
)
