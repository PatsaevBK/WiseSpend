package info.javaway.wiseSpend.features.events.creation

import info.javaway.wiseSpend.base.BaseViewEvent
import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.events.models.SpendEvent
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