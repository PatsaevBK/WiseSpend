package info.javaway.spend_sense.categories.extensions

import info.javaway.spend_sense.categories.creation.CreateCategoryData
import info.javaway.spend_sense.categories.models.Category
import info.javaway.spend_sense.categories.models.CategoryApi
import info.javaway.spend_sense.platform.randomUUID
import kotlinx.datetime.LocalDateTime

fun CreateCategoryData.toCategory(dateTime: LocalDateTime) = Category(
    id = randomUUID(),
    title = title,
    description = subtitle,
    createdAt = dateTime,
    updatedAt = dateTime,
    colorHex = colorHex,
)

fun Category.toApi() = CategoryApi(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
    colorHex = colorHex,
)