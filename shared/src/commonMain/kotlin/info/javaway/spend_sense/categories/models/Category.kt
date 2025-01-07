package info.javaway.spend_sense.categories.models

import db.categories.CategoryTable
import info.javaway.spend_sense.extensions.now
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

fun CategoryTable.toCategory() = Category(
    id = id,
    title = title,
    description = description.orEmpty(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    colorHex = colorHex
)

fun Category.toDb() = CategoryTable(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
    colorHex = colorHex
)
