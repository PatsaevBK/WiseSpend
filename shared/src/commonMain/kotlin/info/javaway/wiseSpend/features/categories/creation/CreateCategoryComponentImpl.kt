package info.javaway.wiseSpend.features.categories.creation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateCategoryComponentImpl(
    private val onDismiss: () -> Unit,
    private val onSaveCategory: (state: CreateCategoryComponent.State) -> Unit,
) : CreateCategoryComponent {

    private val _model = MutableStateFlow(CreateCategoryComponent.State.DEFAULT)
    override val model: StateFlow<CreateCategoryComponent.State> = _model.asStateFlow()

    override fun changeTitle(title: String) {
        _model.update { it.copy(title = title) }
    }

    override fun changeSubtitle(subtitle: String) {
        _model.update { it.copy(subtitle = subtitle) }
    }

    override fun changeColor(color: CreateCategoryComponent.State.Color) {
        _model.update { it.copy(color = color) }
    }

    override fun saveCategory() {
        onSaveCategory.invoke(model.value)
    }

    override fun onDismiss() {
        onDismiss.invoke()
    }
}