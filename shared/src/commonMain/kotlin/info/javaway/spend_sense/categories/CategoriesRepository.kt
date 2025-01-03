package info.javaway.spend_sense.categories

import info.javaway.spend_sense.categories.models.Category
import kotlinx.coroutines.flow.flow

class CategoriesRepository {
    fun getAllFlow() = flow {
        emit(List(20) { Category.NONE.copy(id = it.toString(), title = "category $it") })
    }

    suspend fun createCategory(category: Category) = Unit
}