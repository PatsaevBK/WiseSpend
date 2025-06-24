package info.javaway.wiseSpend.features.categories.list.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.features.categories.creation.compose.CreateCategoryView
import info.javaway.wiseSpend.features.categories.list.CategoriesListComponent
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppTopBar
import info.javaway.wiseSpend.uiLibrary.ui.atoms.FAB
import info.javaway.wiseSpend.uiLibrary.ui.atoms.RootBox
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.categories

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    component: CategoriesListComponent,
    modifier: Modifier = Modifier,
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val dialogSlot by component.dialogSlot.subscribeAsState()

    Scaffold(
        modifier = modifier,
        containerColor = AppThemeProvider.colorsSystem.fill.primary,
        floatingActionButton = {
            FAB { component.openCreateCategory() }
        },
        topBar = {
            AppTopBar(text = stringResource(Res.string.categories))
        }
    ) {
        RootBox(Modifier.padding(it)) {
            CategoriesListView(
                component = component,
                modifier = Modifier,
            ) { category: Category ->

            }
        }
    }

    dialogSlot.child?.instance?.let { categoryComponent ->
        ModalBottomSheet(
            onDismissRequest = { categoryComponent.onDismiss() },
            sheetState = sheetState,
            containerColor = AppThemeProvider.colorsSystem.fill.secondary,
        ) {
            CreateCategoryView(component = categoryComponent)
        }
    }
}