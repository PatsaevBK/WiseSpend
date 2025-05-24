package info.javaway.wiseSpend.features.events.models

import info.javaway.wiseSpend.features.categories.models.Category

data class SpendEventUI(
    val id: String,
    val category: Category,
    val title: String,
    val cost: Double
)