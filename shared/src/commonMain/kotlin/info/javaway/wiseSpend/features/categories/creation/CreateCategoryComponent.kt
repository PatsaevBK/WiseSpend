package info.javaway.wiseSpend.features.categories.creation

import kotlinx.coroutines.flow.StateFlow

interface CreateCategoryComponent {
    val model: StateFlow<State>

    fun changeTitle(title: String)
    fun changeSubtitle(subtitle: String)
    fun changeColor(color: State.Color)
    fun saveCategory()
    fun onDismiss()

    data class State(
        val title: String,
        val subtitle: String,
        val color: Color,
    ) {
        data class Color(
            val red: Float,
            val green: Float,
            val blue: Float,
        )

        companion object {
            val DEFAULT = State(
                title = "",
                subtitle = "",
                color = Color(red = 0.3f, green = 0.3f, blue = 0.3f)
            )
        }
    }
}