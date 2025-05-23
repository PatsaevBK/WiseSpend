package info.javaway.wiseSpend.categories.models

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import db.categories.CategoryTable
import info.javaway.wiseSpend.db.AppDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class CategoryDao(
    db: AppDb,
    private val coroutineContext: CoroutineContext,
) {

    private val categoryQueries = db.categoryTableQueries

    fun getAll(): List<Category> = categoryQueries
        .getAll()
        .executeAsList()
        .map { it.toCategory() }

    fun getAllFlow(): Flow<List<Category>> = categoryQueries
        .getAll()
        .asFlow()
        .mapToList(coroutineContext)
        .map { categories -> categories.map(CategoryTable::toCategory) }

    fun get(id: String) = categoryQueries
        .get(id)
        .executeAsOneOrNull()
        ?.toCategory()

    suspend fun insert(category: Category) = categoryQueries
        .insert(category.toDb())

    suspend fun insertAll(categories: List<Category>) = categoryQueries
        .transaction {
            categories.forEach { categoryQueries.insert(it.toDb()) }
        }


    suspend fun delete(id: String) = categoryQueries
        .delete(id)
}