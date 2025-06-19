package info.javaway.wiseSpend.uiLibrary.ui.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@Composable
fun FAB(
    modifier: Modifier = Modifier,
    clickListener: () -> Unit
) {
    FloatingActionButton(
        onClick = clickListener,
        modifier = modifier.padding(16.dp),
        containerColor = AppThemeProvider.colorsSystem.icon.active
    ) {
        Image(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppThemeProvider.colorsSystem.icon.controls),
            modifier = Modifier.size(32.dp)
        )
    }
}