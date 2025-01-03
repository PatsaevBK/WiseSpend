package info.javaway.spend_sense.events.models

import info.javaway.spend_sense.categories.models.Category

data class SpendEventUI(
    val id: String,
    val category: Category,
    val title: String,
    val cost: Double
)