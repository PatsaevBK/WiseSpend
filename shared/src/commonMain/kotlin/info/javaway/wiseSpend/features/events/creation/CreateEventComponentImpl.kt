package info.javaway.wiseSpend.features.events.creation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.categories.data.CategoriesRepository
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponent
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponentImpl
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.events.creation.CreateEventContract.State
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.platform.randomUUID
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

class CreateEventComponentImpl(
    componentContext: ComponentContext,
    initialDate: CalendarDay?,
    categoriesRepository: CategoriesRepository,
    private val onSave: (SpendEvent) -> Unit,
) : CreateEventComponent, ComponentContext by componentContext {
    
    private val _model = MutableStateFlow(State.NONE.copy(date = initialDate?.date ?: LocalDate.now()))
    override val model: StateFlow<State> = _model.asStateFlow()

    private val nav = SlotNavigation<Config>()
    override val dialogSlot: Value<ChildSlot<*, CategoriesListComponent>> = childSlot(
        source = nav,
        serializer = Config.serializer(),
        childFactory = { _, ctx ->
            CategoriesListComponentImpl(
                componentContext = ctx,
                categoriesRepository = categoriesRepository
            )
        }
    )

    override fun selectDate(date: LocalDate?) = _model.update { it.copy(date = date ?: LocalDate.now()) }
    override fun resetState() = _model.update { State.NONE }
    override fun changeTitle(title: String) = _model.update { it.copy(title = title) }
    override fun changeNote(note: String) = _model.update { it.copy(note = note) }
    override fun changeCost(cost: String) = _model.update { it.copy(cost = cost.toDoubleOrNull() ?: it.cost) }
    override fun selectCategory(category: Category) = _model.update { it.copy(category = category) }

    override fun dismissCategory() = nav.dismiss()

    override fun showCategory() = nav.activate(Config)

    override fun finish() {
        val spendEvent = with(model.value) {
            val now = LocalDateTime.now()
            SpendEvent(
                id = randomUUID(),
                title = title,
                cost = cost,
                date = date,
                categoryId = category.id,
                createdAt = now,
                updatedAt = now,
                note = note
            )
        }
        resetState()
        onSave.invoke(spendEvent)
    }

    @Serializable
    private data object Config
}