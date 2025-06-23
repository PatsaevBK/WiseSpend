package info.javaway.wiseSpend.uiLibrary.ui.atoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@Composable
fun AppNumberTextField(
    value: String,
    modifier: Modifier = Modifier,
    currency: String = "RUB",
    hint: String,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    onValueChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.padding(horizontal = 4.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = hint,
                    style = AppThemeProvider.typography.s.body,
                    color = AppThemeProvider.colorsSystem.text.secondary
                )
            },
            textStyle = AppThemeProvider.typography.l.body.copy(
                color = AppThemeProvider.colorsSystem.text.primary,
                textAlign = TextAlign.Center
            ),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = AppThemeProvider.colorsSystem.fill.active,
                focusedBorderColor = AppThemeProvider.colorsSystem.fill.transparentDark,
                unfocusedBorderColor = AppThemeProvider.colorsSystem.fill.transparentDark,
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = AppThemeProvider.colorsSystem.text.primary,
                    style = AppThemeProvider.typography.l.body
                )
            },
            singleLine = true,
            keyboardOptions = keyboardOptions,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = currency,
            style = AppThemeProvider.typography.l.body,
            color = AppThemeProvider.colorsSystem.text.secondary
        )
    }
}