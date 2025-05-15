package info.javaway.spend_sense.categories.compose

import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import info.javaway.spend_sense.categories.creation.compose.CreateCategoryView
import info.javaway.spend_sense.categories.list.CategoriesListComponent
import info.javaway.spend_sense.categories.list.compose.CategoriesListView
import info.javaway.spend_sense.categories.models.Category
import info.javaway.spend_sense.common.ui.atoms.FAB
import kotlinx.coroutines.launch

@Composable
fun CategoriesScreen(
    component: CategoriesListComponent,
) {

    val sheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = {
            CreateCategoryView(
                isExpanded = sheetState.currentValue == ModalBottomSheetValue.Expanded,
            ) {
                scope.launch { sheetState.hide() }
                component.createCategory(it)
            }
        },
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
    ) {
        CategoriesListView(
            component = component,
            modifier = Modifier,
        ) { category: Category ->

        }

        FAB {
            scope.launch {
                sheetState.show()
            }
        }
    }
}