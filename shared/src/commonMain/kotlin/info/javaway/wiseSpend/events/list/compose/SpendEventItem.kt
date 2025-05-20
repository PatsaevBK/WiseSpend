package info.javaway.wiseSpend.events.list.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.javaway.wiseSpend.common.ui.atoms.ColorLabel
import info.javaway.wiseSpend.common.ui.theme.AppThemeProvider
import info.javaway.wiseSpend.events.models.SpendEventUI
import info.javaway.wiseSpend.extensions.fromHex
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.empty_category

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun SpendEventItem(event: SpendEventUI) {

    val categoryColor = event.category.colorHex.takeIf { it.isNotBlank() }
        ?.let { Color.fromHex(it) } ?: AppThemeProvider.colors.accent

    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp)
            .background(
                AppThemeProvider.colors.surface.copy(0.8f),
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            if (event.title.isNotBlank()) {
                Text(
                    event.title,
                    modifier = Modifier.padding(bottom = 2.dp),
                    fontSize = 20.sp,
                    color = AppThemeProvider.colors.onSurface
                )
            }
            Text(
                event.category.title.ifEmpty { stringResource(Res.string.empty_category) },
                fontSize = 16.sp,
                color = categoryColor,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            Text(
                event.cost.toString(),
                fontSize = 16.sp,
                color = AppThemeProvider.colors.onSurface.copy(0.8f)
            )
        }

        ColorLabel(categoryColor.toArgb().toHexString())
    }

}