package info.javaway.wiseSpend.features.categories

import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.categories.models.CategoryDao

class CategoriesRepository(
    private val dao: CategoryDao
) {
    fun getAllFlow() = dao.getAllFlow()
    fun getAll() = dao.getAll()
    suspend fun insertAll(categories: List<Category>) = dao.insertAll(categories)

    suspend fun createCategory(category: Category) = dao.insert(category)
}