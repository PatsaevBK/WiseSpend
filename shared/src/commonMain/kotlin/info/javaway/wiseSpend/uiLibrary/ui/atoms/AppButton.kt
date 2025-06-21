package info.javaway.wiseSpend.uiLibrary.ui.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = AppThemeProvider.colorsSystem.icon.primary,
            containerColor = AppThemeProvider.colorsSystem.fill.secondary,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = AppThemeProvider.colors.onSurface
        )
    ) {
        Text(
            text = title,
            style = AppThemeProvider.typography.l.body,
            color = AppThemeProvider.colorsSystem.text.primary
        )
    }
}