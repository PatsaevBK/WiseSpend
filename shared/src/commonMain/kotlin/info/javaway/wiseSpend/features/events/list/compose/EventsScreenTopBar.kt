package info.javaway.wiseSpend.features.events.list.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppAccountsButton
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreenTopBar(
    accountName: String,
    icon: Painter,
    accountAmount: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Text(
                    text = "WiseSpends",
                    style = AppThemeProvider.typography.l.heading2,
                    color = AppThemeProvider.colorsSystem.text.primary,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = AppThemeProvider.colorsSystem.fill.primary
            ),
            actions = {
                AppAccountsButton(
                    text = accountName,
                    amount = accountAmount,
                    onClick = onClick,
                    icon = icon,
                )
            }
        )
        HorizontalDivider(thickness = 1.dp, color = AppThemeProvider.colorsSystem.separator.primary)
    }
}