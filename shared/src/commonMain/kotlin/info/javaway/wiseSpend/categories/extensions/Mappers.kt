package info.javaway.wiseSpend.categories.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import info.javaway.wiseSpend.categories.creation.CreateCategoryComponent
import info.javaway.wiseSpend.categories.models.Category
import info.javaway.wiseSpend.categories.models.CategoryApi
import info.javaway.wiseSpend.platform.randomUUID
import kotlinx.datetime.LocalDateTime

fun Category.toApi() = CategoryApi(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
    colorHex = colorHex,
)

fun CreateCategoryComponent.State.toCategory(dateTime: LocalDateTime) = Category(
    id = randomUUID(),
    title = title,
    description = subtitle,
    createdAt = dateTime,
    updatedAt = dateTime,
    colorHex = Color(red = color.red, blue = color.blue, green = color.green).toArgb().toString(16),
)