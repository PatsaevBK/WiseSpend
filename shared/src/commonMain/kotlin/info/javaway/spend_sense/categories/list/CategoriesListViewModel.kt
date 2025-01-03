package info.javaway.spend_sense.categories.list

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.creation.CreateCategoryData
import info.javaway.spend_sense.categories.extensions.toCategory
import info.javaway.spend_sense.categories.list.CategoriesListContract.Event
import info.javaway.spend_sense.categories.list.CategoriesListContract.State
import info.javaway.spend_sense.extensions.now
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class CategoriesListViewModel(
    private val categoriesRepository: CategoriesRepository,
): BaseViewModel<State, Event>() {

    init {
        activate()
    }

    fun createCategory(createCategoryData: CreateCategoryData) {
        viewModelScope.launch {
            categoriesRepository.createCategory(createCategoryData.toCategory(LocalDateTime.now()))
        }
    }

    private fun activate() {
        categoriesRepository.getAllFlow().onEach {
            updateState { copy(categories = it) }
        }.launchIn(viewModelScope)
    }

    override fun initialState(): State = State.NONE

}