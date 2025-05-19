package info.javaway.spend_sense.categories.list

import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.categories.models.Category

interface CategoriesListContract {

    data class State(
        val categories: List<Category>
    ): BaseViewState {
        companion object {
            val NONE = State(emptyList())
        }
    }
}