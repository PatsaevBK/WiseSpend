package info.javaway.wiseSpend.features.categories.data

import info.javaway.wiseSpend.features.categories.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CategoriesRepositoryImpl(
    private val dao: CategoryDao
): CategoriesRepository {
    override fun getAllFlow() =
        dao.getAllFlow().flowOn(Dispatchers.IO)
    override suspend fun getAll() =
        withContext(Dispatchers.IO) { dao.getAll() }
    override suspend fun getById(categoryId: String) =
        withContext(Dispatchers.IO) { dao.get(categoryId) }

    override suspend fun insertAll(categories: List<Category>) =
        withContext(Dispatchers.IO) { dao.insertAll(categories) }
    override suspend fun create(category: Category) =
        withContext(Dispatchers.IO) { dao.insert(category) }
}