package info.javaway.wiseSpend.features.events.creation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponent
import info.javaway.wiseSpend.features.categories.models.Category
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface CreateEventComponent {

    val model: StateFlow<CreateEventContract.State>
    val dialogSlot: Value<ChildSlot<*, CategoriesListComponent>>

    fun selectDate(date: LocalDate?)
    fun changeTitle(title: String)
    fun changeNote(note: String)
    fun changeCost(cost: String)
    fun selectCategory(category: Category)

    fun showCategory()
    fun dismissCategory()

    fun resetState()
    fun finish()
}