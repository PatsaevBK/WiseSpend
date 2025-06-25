package info.javaway.wiseSpend.features.categories.data

import info.javaway.wiseSpend.features.categories.models.Category

class CategoriesRepositoryImpl(
    private val dao: CategoryDao
): CategoriesRepository {
    override fun getAllFlow() = dao.getAllFlow()
    override fun getAll() = dao.getAll()
    override fun getById(categoryId: String) = dao.get(categoryId)

    override fun insertAll(categories: List<Category>) = dao.insertAll(categories)
    override fun create(category: Category) = dao.insert(category)
}