package info.javaway.wiseSpend.categories.list.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import info.javaway.wiseSpend.categories.list.CategoriesListComponent
import info.javaway.wiseSpend.categories.models.Category

@Composable
fun CategoriesListView(
    component: CategoriesListComponent,
    modifier: Modifier = Modifier,
    onClick: (Category) -> Unit,
) {
    val state by component.model.collectAsState()

    LazyColumn(modifier = modifier) {
        items(state.categories) {
            CategoryItem(category = it, onClick = { onClick.invoke(it) })
        }
    }
}