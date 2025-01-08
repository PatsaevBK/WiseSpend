package info.javaway.spend_sense.categories

import info.javaway.spend_sense.categories.models.Category
import info.javaway.spend_sense.categories.models.CategoryDao

class CategoriesRepository(
    private val dao: CategoryDao
) {
    fun getAllFlow() = dao.getAllFlow()

    suspend fun createCategory(category: Category) = dao.insert(category)
}