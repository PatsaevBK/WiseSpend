package info.javaway.wiseSpend.features.accounts.list.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.features.accounts.creation.compose.CreateAccountView
import info.javaway.wiseSpend.features.accounts.list.AccountsListComponent
import info.javaway.wiseSpend.uiLibrary.ui.atoms.FAB
import info.javaway.wiseSpend.uiLibrary.ui.atoms.RootBox
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsListScreen(
    component: AccountsListComponent,
    modifier: Modifier = Modifier,
) {
    
    val model by component.model.collectAsState()
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
        RootBox(Modifier.padding(paddingValues)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(model.accountsUI) { account ->
                    if (account.id != null) {
                        AccountCell(
                            title = account.name,
                            subtitle = account.formattedAmount,
                            editable = true,
                            modifier = Modifier.clickable {
                                component.changeAccount(accountId = account.id)
                            }
                        )
                    } else {
                        AccountCell(
                            title = account.name,
                            subtitle = account.formattedAmount,
                            editable = false,
                        )
                    }
                    HorizontalDivider(color = AppThemeProvider.colorsSystem.separator.primary)
                }
            }
        }
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