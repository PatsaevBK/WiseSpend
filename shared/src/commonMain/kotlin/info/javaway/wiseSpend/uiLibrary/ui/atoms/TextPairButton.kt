package info.javaway.wiseSpend.uiLibrary.ui.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.extensions.fromHex
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@Composable
fun TextPairButton(
    title: String,
    subtitle: String? = null,
    buttonTitle: String,
    colorHex: String? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    val color = colorHex?.let { Color.fromHex(it) } ?: AppThemeProvider.colorsSystem.fill.label.grey

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
            Text(
                text = title,
                style = AppThemeProvider.typography.l.body,
                color = AppThemeProvider.colorsSystem.text.primary,
            )
            if(subtitle != null){
                Text(
                    text = subtitle,
                    style = AppThemeProvider.typography.l.body,
                    color = AppThemeProvider.colorsSystem.text.secondary,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = AppThemeProvider.colorsSystem.fill.secondary,
            ),
            border = BorderStroke(1.dp, color)
        ){
            Text(buttonTitle, color = AppThemeProvider.colorsSystem.text.caption, style = AppThemeProvider.typography.l.body)
        }
    }

}