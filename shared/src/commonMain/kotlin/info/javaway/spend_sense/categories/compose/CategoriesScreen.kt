package info.javaway.spend_sense.categories.compose

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import info.javaway.spend_sense.categories.creation.compose.CreateCategoryView
import info.javaway.spend_sense.categories.list.CategoriesListViewModel
import info.javaway.spend_sense.categories.list.compose.CategoriesListView
import info.javaway.spend_sense.categories.models.Category
import info.javaway.spend_sense.common.ui.atoms.FAB
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxScope.CategoriesScreen(
    viewModel: CategoriesListViewModel,
    isBottomBarVisible: MutableState<Boolean>
) {

    val sheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = {
            //create category
            CreateCategoryView(isExpanded = sheetState.currentValue == ModalBottomSheetValue.Expanded) {
                isBottomBarVisible.value = true
                scope.launch { sheetState.hide() }
                viewModel.createCategory(it)
            }
        },
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
    ) {
        CategoriesListView(
            viewModel = viewModel,
            modifier = Modifier,
        ) { category: Category ->

        }

        FAB {
            scope.launch {
                isBottomBarVisible.value = false
                sheetState.show()
            }
        }
    }
}