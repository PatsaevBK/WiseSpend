package info.javaway.wiseSpend.mocks

import info.javaway.wiseSpend.features.categories.data.CategoriesRepository
import info.javaway.wiseSpend.features.categories.models.Category
import kotlinx.coroutines.flow.Flow

internal class CategoriesRepositoryMock: CategoriesRepository {
    override fun getAllFlow(): Flow<List<Category>> {
        TODO("Not yet implemented")
    }

    var stubbedCategories: List<Category> = emptyList()
    override suspend fun getAll(): List<Category> {
        return stubbedCategories
    }

    var stubbedCategory: Category? = null
    override suspend fun getById(categoryId: String): Category? {
        return stubbedCategory
    }

    override suspend fun insertAll(categories: List<Category>) {
        TODO("Not yet implemented")
    }

    override suspend fun create(category: Category) {
        TODO("Not yet implemented")
    }
}