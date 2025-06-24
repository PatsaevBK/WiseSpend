package info.javaway.wiseSpend.features.categories.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import db.categories.CategoryTable
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.categories.creation.CreateCategoryComponent
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.categories.models.CategoryApi
import info.javaway.wiseSpend.platform.randomUUID
import kotlinx.datetime.LocalDateTime


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

fun Category.toApi() = CategoryApi(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
    colorHex = colorHex,
)

fun CategoryApi.toEntity() = Category(
    id = id.orEmpty(),
    title = title.orEmpty(),
    description = description.orEmpty(),
    createdAt = createdAt ?: LocalDateTime.now(),
    updatedAt = updatedAt ?: LocalDateTime.now(),
    colorHex = colorHex.orEmpty(),
)