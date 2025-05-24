package info.javaway.wiseSpend.features.categories.list.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.features.categories.creation.compose.CreateCategoryView
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponent
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.uiLibrary.ui.atoms.FAB
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    component: CategoriesListComponent,
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val dialogSlot by component.dialogSlot.subscribeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        CategoriesListView(
            component = component,
            modifier = Modifier,
        ) { category: Category ->

        }

        FAB(modifier = Modifier.align(Alignment.BottomEnd)) {
            component.openCreateCategory()
        }
    }

    dialogSlot.child?.instance?.let { categoryComponent ->
        ModalBottomSheet(
            onDismissRequest = { categoryComponent.onDismiss() },
            sheetState = sheetState,
            containerColor = AppThemeProvider.colors.surface,
        ) {
            CreateCategoryView(component = categoryComponent)
        }
    }
}