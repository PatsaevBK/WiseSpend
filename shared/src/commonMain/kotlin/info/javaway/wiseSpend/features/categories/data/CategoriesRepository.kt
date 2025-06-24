package info.javaway.wiseSpend.features.categories.data

import info.javaway.wiseSpend.features.categories.models.Category

class CategoriesRepository(
    private val dao: CategoryDao
) {
    fun getAllFlow() = dao.getAllFlow()
    fun getAll() = dao.getAll()
    fun getById(categoryId: String) = dao.get(categoryId)

    fun insertAll(categories: List<Category>) = dao.insertAll(categories)
    fun create(category: Category) = dao.insert(category)
}