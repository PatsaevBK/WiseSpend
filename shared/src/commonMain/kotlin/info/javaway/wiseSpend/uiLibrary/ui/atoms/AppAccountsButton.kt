package info.javaway.wiseSpend.uiLibrary.ui.atoms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@Composable
fun AppAccountsButton(
    text: String,
    icon: Painter,
    amount: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Row(
            modifier = Modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = text,
                style = AppThemeProvider.typography.l.heading3,
                color = AppThemeProvider.colorsSystem.text.primary
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Show all accounts",
                tint = AppThemeProvider.colorsSystem.icon.primary,
            )
        }
        Text(text = amount, style = AppThemeProvider.typography.l.heading3, color = AppThemeProvider.colorsSystem.text.primary)
    }
}