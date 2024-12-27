package info.javaway.spend_sense.categories.models

import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.LocalDateTime

data class Category(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updateAt: LocalDateTime,
    val colorHex: String,
) {
    companion object {
        val NONE = Category(
            id = "",
            title = "",
            description = "",
            createdAt = LocalDateTime.now(),
            updateAt = LocalDateTime.now(),
            colorHex = "",
        )
    }
}
