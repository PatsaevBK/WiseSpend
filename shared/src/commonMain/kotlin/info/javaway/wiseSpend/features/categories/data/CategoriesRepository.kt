package info.javaway.wiseSpend.features.categories.data

import info.javaway.wiseSpend.features.categories.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun getAllFlow(): Flow<List<Category>>
    fun getAll(): List<Category>
    fun getById(categoryId: String): Category?

    fun insertAll(categories: List<Category>)
    fun create(category: Category)
}