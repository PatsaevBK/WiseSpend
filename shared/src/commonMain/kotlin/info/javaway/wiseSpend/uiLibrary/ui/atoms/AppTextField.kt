package info.javaway.wiseSpend.uiLibrary.ui.atoms

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@Composable
fun AppTextField(
    value: String,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = AppThemeProvider.typography.l.body.copy(color = AppThemeProvider.colorsSystem.text.primary),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            cursorColor = AppThemeProvider.colorsSystem.fill.active,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = placeholder,
                color = AppThemeProvider.colorsSystem.text.secondary,
                style = AppThemeProvider.typography.l.body
            )
        },
        keyboardOptions = keyboardOptions
    )
}