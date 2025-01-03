package info.javaway.spend_sense.categories.list.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.categories.list.CategoriesListViewModel
import info.javaway.spend_sense.categories.models.Category

@Composable
fun CategoriesListView(
    viewModel: CategoriesListViewModel,
    modifier: Modifier = Modifier,
    onClick: (Category) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(modifier = modifier) {
        items(state.categories) {
            CategoryItem(category = it, onClick = { onClick.invoke(it) })
        }
    }
}