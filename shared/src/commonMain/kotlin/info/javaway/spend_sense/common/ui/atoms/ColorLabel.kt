package info.javaway.spend_sense.common.ui.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.extensions.fromHex

@Composable
fun ColorLabel(
    modifier: Modifier = Modifier,
    colorHex: String,
) {
    val color = colorHex.let {
        runCatching { Color.fromHex(it) }.getOrDefault(AppThemeProvider.colors.accent)
    }
    Box(
        modifier = modifier
            .size(40.dp)
            .background(color.copy(0.8f), RoundedCornerShape(8.dp))
            .border(2.dp, color, RoundedCornerShape(8.dp))
    )
}