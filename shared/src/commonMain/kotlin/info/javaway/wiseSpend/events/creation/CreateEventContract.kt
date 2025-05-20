package info.javaway.wiseSpend.events.creation

import info.javaway.wiseSpend.base.BaseViewEvent
import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.categories.models.Category
import info.javaway.wiseSpend.events.models.SpendEvent
import info.javaway.wiseSpend.extensions.now
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