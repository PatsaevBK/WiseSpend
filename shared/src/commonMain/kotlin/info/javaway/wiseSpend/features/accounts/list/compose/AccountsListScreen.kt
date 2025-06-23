package info.javaway.wiseSpend.features.accounts.list.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.features.accounts.creation.compose.CreateAccountView
import info.javaway.wiseSpend.features.accounts.list.AccountsListComponent
import info.javaway.wiseSpend.uiLibrary.ui.atoms.FAB
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsListScreen(
    component: AccountsListComponent,
    modifier: Modifier = Modifier,
) {
    val slot by component.slot.subscribeAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        modifier = modifier,
        containerColor = AppThemeProvider.colorsSystem.fill.primary,
        floatingActionButton = {
            FAB { component.newAccount() }
        },
        topBar = {
            AccountsScreenTopBar()
        }
    ) { paddingValues ->
        AccountsListView(component, modifier = Modifier.padding(paddingValues))
    }

    slot.child?.instance?.let { createAccountComponent ->
        ModalBottomSheet(
            onDismissRequest = component::onDismiss,
            sheetState = sheetState,
            containerColor = AppThemeProvider.colorsSystem.fill.secondary,
        ) {
            CreateAccountView(createAccountComponent)
        }
    }
}