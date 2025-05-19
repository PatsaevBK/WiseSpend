package info.javaway.spend_sense.categories.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import info.javaway.spend_sense.categories.creation.CreateCategoryComponent
import kotlinx.coroutines.flow.StateFlow

interface CategoriesListComponent {

    val model: StateFlow<CategoriesListContract.State>
    val dialogSlot: Value<ChildSlot<*, CreateCategoryComponent>>

    fun openCreateCategory()

    interface Factory {
        fun create(componentContext: ComponentContext): CategoriesListComponent
    }
}