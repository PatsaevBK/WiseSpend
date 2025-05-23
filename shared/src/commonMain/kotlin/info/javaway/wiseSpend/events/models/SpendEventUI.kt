package info.javaway.wiseSpend.events.models

import info.javaway.wiseSpend.categories.models.Category

data class SpendEventUI(
    val id: String,
    val category: Category,
    val title: String,
    val cost: Double
)