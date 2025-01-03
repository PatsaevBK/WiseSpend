package info.javaway.spend_sense.events.creation

import info.javaway.spend_sense.base.BaseViewEvent
import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.categories.models.Category
import info.javaway.spend_sense.events.models.SpendEvent
import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.LocalDate

interface CreateEventContract {

    data class State(
        val title: String,
        val category: Category,
        val date: LocalDate,
        val cost: Double,
        val note: String
    ) : BaseViewState {
        companion object {
            val NONE = State(
                title = "",
                category = Category.NONE,
                date = LocalDate.now(),
                cost = 0.0,
                note = ""
            )
        }
    }

    sealed interface Event : BaseViewEvent {
        data class Finish(val spendEvent: SpendEvent) : Event
    }
}