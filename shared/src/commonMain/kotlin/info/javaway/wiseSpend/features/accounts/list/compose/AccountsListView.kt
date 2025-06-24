package info.javaway.wiseSpend.features.accounts.list.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import info.javaway.wiseSpend.features.accounts.list.AccountsListComponent
import info.javaway.wiseSpend.uiLibrary.ui.atoms.RootBox
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider


@Composable
fun AccountsListView(
    component: AccountsListComponent,
    modifier: Modifier = Modifier,
) {
    val model by component.model.collectAsState()

    RootBox(modifier) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(model.accountsUIWithTotals) { account ->
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