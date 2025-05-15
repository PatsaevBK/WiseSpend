package info.javaway.spend_sense.categories.list

import com.arkivanov.decompose.ComponentContext
import info.javaway.spend_sense.categories.creation.CreateCategoryData
import kotlinx.coroutines.flow.StateFlow

interface CategoriesListComponent {

    val model: StateFlow<CategoriesListContract.State>

    fun createCategory(createCategoryData: CreateCategoryData)

    interface Factory {
        fun create(componentContext: ComponentContext): CategoriesListComponent
    }
}