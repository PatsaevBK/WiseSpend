package info.javaway.wiseSpend.features.events.list.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.features.accounts.models.AccountUi
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import org.jetbrains.compose.resources.painterResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.money_bag_outline

@Composable
fun ChooseAccountView(
    accounts: List<AccountUi>,
    selectedAccountId: String?,
    onClick: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.wrapContentWidth().wrapContentHeight(),
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(24.dp).selectableGroup(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = "Select an account",
                style = AppThemeProvider.typography.l.heading3,
                color = AppThemeProvider.colorsSystem.text.primary,
            )

            accounts.forEach { account ->
                RadioItem(
                    selectedAccountId = selectedAccountId,
                    accountName = account.name,
                    accountId = account.id,
                    accountAmount = account.formattedAmount,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun RadioItem(
    selectedAccountId: String?,
    accountId: String?,
    accountName: String,
    accountAmount: String,
    onClick: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .selectable(
                selected = selectedAccountId == accountId, onClick = {
                    onClick.invoke(accountId)
                }, role = Role.RadioButton
            ).fillMaxWidth()
            ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = selectedAccountId == accountId,
            onClick = null,
            colors = RadioButtonDefaults.colors().copy(
                selectedColor = AppThemeProvider.colorsSystem.icon.active,
                unselectedColor = AppThemeProvider.colorsSystem.fill.disable
            )
        )

        Spacer(Modifier.width(8.dp))

        if (accountId == null) {
            Icon(painterResource(Res.drawable.money_bag_outline), null, modifier = Modifier.size(32.dp))
        } else {
            Icon(imageVector = Icons.Outlined.Wallet, contentDescription = null, modifier = Modifier.size(32.dp))
        }

        Spacer(Modifier.width(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = accountName,
                style = AppThemeProvider.typography.l.body,
                color = AppThemeProvider.colorsSystem.text.primary,
                modifier = Modifier
            )
            Text(
                text = accountAmount,
                style = AppThemeProvider.typography.l.body,
                color = AppThemeProvider.colorsSystem.text.secondary
            )
        }
    }
}