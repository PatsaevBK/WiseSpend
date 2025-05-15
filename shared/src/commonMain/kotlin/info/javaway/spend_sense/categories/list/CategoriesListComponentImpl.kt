package info.javaway.spend_sense.categories.list

import com.arkivanov.decompose.ComponentContext
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.creation.CreateCategoryData
import info.javaway.spend_sense.categories.extensions.toCategory
import info.javaway.spend_sense.events.extensions.componentScope
import info.javaway.spend_sense.extensions.now
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class CategoriesListComponentImpl(
    componentContext: ComponentContext,
    private val categoriesRepository: CategoriesRepository
) : CategoriesListComponent, ComponentContext by componentContext {

    private val _model = MutableStateFlow(CategoriesListContract.State.NONE)
    override val model: StateFlow<CategoriesListContract.State> = _model.asStateFlow()

    private val scope = componentScope()

    init {
        categoriesRepository.getAllFlow().onEach {
            _model.update { oldState -> oldState.copy(categories = it) }
        }.launchIn(scope)
    }

    override fun createCategory(createCategoryData: CreateCategoryData) {
        scope.launch {
            categoriesRepository.createCategory(createCategoryData.toCategory(LocalDateTime.now()))
        }
    }

    class Factory(
        private val categoriesRepository: CategoriesRepository
    ) : CategoriesListComponent.Factory {
        override fun create(componentContext: ComponentContext): CategoriesListComponent =
            CategoriesListComponentImpl(
                componentContext = componentContext,
                categoriesRepository = categoriesRepository
            )
    }
}