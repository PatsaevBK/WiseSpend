package info.javaway.wiseSpend.features.categories.models

import db.categories.CategoryTable
import info.javaway.wiseSpend.extensions.now
import kotlinx.datetime.LocalDateTime

data class Category(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val colorHex: String,
) {
    companion object {
        val NONE = Category(
            id = "",
            title = "",
            description = "",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            colorHex = "",
        )
    }
}
