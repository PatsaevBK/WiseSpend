package info.javaway.wiseSpend.features.categories.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.extensions.componentScope
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.categories.data.CategoriesRepository
import info.javaway.wiseSpend.features.categories.creation.CreateCategoryComponent
import info.javaway.wiseSpend.features.categories.creation.CreateCategoryComponentImpl
import info.javaway.wiseSpend.features.categories.data.toCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

class CategoriesListComponentImpl(
    componentContext: ComponentContext,
    private val categoriesRepository: CategoriesRepository
) : CategoriesListComponent, ComponentContext by componentContext {

    private val _model = MutableStateFlow(CategoriesListContract.State.NONE)
    override val model: StateFlow<CategoriesListContract.State> = _model.asStateFlow()

    private val scope = componentScope()

    private val nav = SlotNavigation<Config>()
    private val _dialogSlot = childSlot(
        source = nav,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = { _ , ctx ->
            CreateCategoryComponentImpl(
                componentContext = ctx,
                onDismiss = { nav.dismiss() },
                onSaveCategory = {
                    scope.launch {
                        categoriesRepository.createCategory(it.toCategory(LocalDateTime.now()))
                    }
                    nav.dismiss()
                },
            )
        }
    )

    override val dialogSlot: Value<ChildSlot<*, CreateCategoryComponent>> = _dialogSlot

    init {
        categoriesRepository.getAllFlow().onEach {
            _model.update { oldState -> oldState.copy(categories = it) }
        }.launchIn(scope)
    }

    override fun openCreateCategory() {
        nav.activate(Config)
    }

    @Serializable
    private data object Config

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