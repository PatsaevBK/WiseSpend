package info.javaway.spend_sense.common.ui.atoms

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(
            contentColor = AppThemeProvider.colors.surface,
            containerColor = AppThemeProvider.colors.onSurface
        )
    ) {
        content()
    }
}