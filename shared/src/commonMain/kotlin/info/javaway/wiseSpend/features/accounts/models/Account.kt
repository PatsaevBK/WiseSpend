package info.javaway.wiseSpend.features.accounts.models

import kotlinx.datetime.LocalDateTime

data class Account(
    val id: String,
    val name: String,
    val amount: Double,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
