package info.javaway.wiseSpend.features.categories.creation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.arkivanov.decompose.ComponentContext
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.platform.randomUUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDateTime

class CreateCategoryComponentImpl(
    private val onDismiss: () -> Unit,
    private val onSaveCategory: (Category) -> Unit,
    componentContext: ComponentContext
) : CreateCategoryComponent, ComponentContext by componentContext {

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
        val newCategory = with(model.value) {
            val now = LocalDateTime.now()
            Category(
                 id = randomUUID(),
                 title = title,
                 description = subtitle,
                 createdAt = now,
                 updatedAt = now,
                 colorHex = Color(red = color.red, blue = color.blue, green = color.green).toArgb().toString(16),
            )
        }
        onSaveCategory.invoke(newCategory)
    }

    override fun onDismiss() {
        onDismiss.invoke()
    }
}