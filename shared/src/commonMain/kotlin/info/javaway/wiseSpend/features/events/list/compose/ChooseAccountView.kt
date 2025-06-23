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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.features.accounts.list.AccountsListComponent
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import org.jetbrains.compose.resources.painterResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.money_bag_outline

@Composable
fun ChooseAccountView(
    accountListComponent: AccountsListComponent,
    selectedAccountId: String?,
    isTotalsInclude: Boolean,
    modifier: Modifier = Modifier,
    onClick: (String?) -> Unit = { },
) {
    val model by accountListComponent.model.collectAsState()
    val walletIcon = rememberVectorPainter(Icons.Outlined.Wallet)

    Surface(
        modifier = modifier.wrapContentWidth().wrapContentHeight(),
        color = AppThemeProvider.colorsSystem.fill.secondary,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(24.dp).selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Select an account",
                style = AppThemeProvider.typography.l.heading3,
                color = AppThemeProvider.colorsSystem.text.primary,
            )

            if (isTotalsInclude) {
                model.accountsUIWithTotals.forEach { account ->
                    val isSelected = selectedAccountId == account.id
                    val painter = if (account.id == null) {
                        painterResource(Res.drawable.money_bag_outline)
                    } else {
                        walletIcon
                    }

                    RadioItem(
                        isSelected = isSelected,
                        painter = painter,
                        title = account.name,
                        subtitle = account.formattedAmount,
                        onClick = { onClick.invoke(account.id) }
                    )
                }
            } else {
                model.accountsUI.forEach { account ->
                    val isSelected = selectedAccountId == account.id
                    RadioItem(
                        isSelected = isSelected,
                        painter = walletIcon,
                        title = account.name,
                        subtitle = account.formattedAmount,
                        onClick = { onClick.invoke(account.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RadioItem(
    isSelected: Boolean,
    painter: Painter,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.RadioButton,
            ).fillMaxWidth()
            ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors().copy(
                selectedColor = AppThemeProvider.colorsSystem.icon.active,
                unselectedColor = AppThemeProvider.colorsSystem.fill.disable
            )
        )

        Spacer(Modifier.width(8.dp))

        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = AppThemeProvider.colorsSystem.icon.primary
        )

        Spacer(Modifier.width(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                style = AppThemeProvider.typography.l.body,
                color = AppThemeProvider.colorsSystem.text.primary,
                modifier = Modifier
            )
            Text(
                text = subtitle,
                style = AppThemeProvider.typography.l.body,
                color = AppThemeProvider.colorsSystem.text.secondary
            )
        }
    }
}