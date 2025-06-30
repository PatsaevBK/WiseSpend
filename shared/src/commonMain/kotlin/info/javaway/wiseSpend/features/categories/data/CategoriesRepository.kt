package info.javaway.wiseSpend.features.categories.data

import info.javaway.wiseSpend.features.categories.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun getAllFlow(): Flow<List<Category>>
    suspend fun getAll(): List<Category>
    suspend fun getById(categoryId: String): Category?

    suspend fun insertAll(categories: List<Category>)
    suspend fun create(category: Category)
}