package info.javaway.wiseSpend.uiLibrary.ui.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    text: String,
) {
    Column(modifier) {
        TopAppBar(
            title = {
                Text(
                    text = text,
                    style = AppThemeProvider.typography.l.heading2,
                    color = AppThemeProvider.colorsSystem.text.primary,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = AppThemeProvider.colorsSystem.fill.primary
            ),
            modifier = modifier
        )
        HorizontalDivider(thickness = 1.dp, color = AppThemeProvider.colorsSystem.separator.secondary)
    }
}