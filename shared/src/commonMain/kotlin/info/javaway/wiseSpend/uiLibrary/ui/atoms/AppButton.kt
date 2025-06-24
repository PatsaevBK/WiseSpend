package info.javaway.wiseSpend.uiLibrary.ui.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@Composable
fun AppButton(
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClickWhenDisabled: () -> Unit = { },
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = AppThemeProvider.colorsSystem.icon.controls,
            disabledContentColor = AppThemeProvider.colorsSystem.icon.controls,
            containerColor = AppThemeProvider.colorsSystem.icon.active,
            disabledContainerColor = AppThemeProvider.colorsSystem.fill.disable
        ),
        border = BorderStroke(
            width = 1.dp,
            color = AppThemeProvider.colorsSystem.separator.secondary
        )
    ) {
        Text(
            text = title,
            style = AppThemeProvider.typography.l.body,
            color = AppThemeProvider.colorsSystem.text.controls
        )
    }
}