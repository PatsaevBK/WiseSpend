package info.javaway.wiseSpend.features.categories.list

import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.features.categories.models.Category

interface CategoriesListContract {

    data class State(
        val categories: List<Category>
    ): BaseViewState {
        companion object {
            val NONE = State(emptyList())
        }
    }
}