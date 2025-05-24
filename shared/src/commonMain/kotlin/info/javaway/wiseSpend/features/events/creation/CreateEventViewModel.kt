package info.javaway.wiseSpend.features.events.creation

import info.javaway.wiseSpend.base.BaseViewModel
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.events.creation.CreateEventContract.State
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.platform.randomUUID
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class CreateEventViewModel : BaseViewModel<State, CreateEventContract.Event>() {

    override fun initialState() = State.NONE

    fun selectDate(date: LocalDate?) = updateState { copy(date = date ?: LocalDate.now()) }
    fun resetState() = updateState { State.NONE }
    fun changeTitle(title: String) = updateState { copy(title = title) }
    fun changeNote(note: String) = updateState { copy(note = note) }
    fun changeCost(cost: String) = updateState { copy(cost = cost.toDoubleOrNull() ?: this.cost) }
    fun selectCategory(category: Category) = updateState { copy(category = category) }

    fun finish() {
        val spendEvent = with(state.value){
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
        pushEvent(CreateEventContract.Event.Finish(spendEvent))
    }
}