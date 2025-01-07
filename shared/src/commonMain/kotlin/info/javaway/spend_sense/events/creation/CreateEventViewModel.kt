package info.javaway.spend_sense.events.creation

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.categories.models.Category
import info.javaway.spend_sense.events.creation.CreateEventContract.*
import info.javaway.spend_sense.events.models.SpendEvent
import info.javaway.spend_sense.extensions.now
import info.javaway.spend_sense.platform.randomUUID
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class CreateEventViewModel : BaseViewModel<State, Event>() {

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
            )
        }
        resetState()
        pushEvent(Event.Finish(spendEvent))
    }
}