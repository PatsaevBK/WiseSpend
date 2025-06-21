package info.javaway.wiseSpend.features.settings.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.dark_theme

@Composable
fun ThemeSwitch(
    checked: Boolean,
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(AppThemeProvider.colorsSystem.fill.card.grey, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(Res.string.dark_theme),
            modifier = Modifier.weight(1f),
            color = AppThemeProvider.colorsSystem.text.primary,
            style = AppThemeProvider.typography.l.body,
        )
        Switch(
            checked = checked,
            onCheckedChange = onSwitch,
            colors = SwitchDefaults.colors(
                checkedThumbColor = AppThemeProvider.colorsSystem.icon.controls,
                checkedTrackColor = AppThemeProvider.colorsSystem.fill.success,
                uncheckedTrackColor = AppThemeProvider.colorsSystem.fill.disable,
                uncheckedThumbColor = AppThemeProvider.colorsSystem.icon.controls,
            )
        )
    }
}