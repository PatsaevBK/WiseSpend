package info.javaway.wiseSpend.features.accounts.list.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@Composable
fun AccountCell(
    title: String,
    subtitle: String?,
    editable: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 72.dp)
            .background(AppThemeProvider.colorsSystem.fill.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 12.dp,
                    top = 14.dp,
                    bottom = 14.dp,
                )
                .weight(1f)
        ) {
            Text(
                text = title,
                style = AppThemeProvider.typography.m.heading3,
                color = AppThemeProvider.colorsSystem.text.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            subtitle?.let {
                Text(
                    text = subtitle,
                    style = AppThemeProvider.typography.m.body,
                    color = AppThemeProvider.colorsSystem.text.secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        if (editable) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                tint = AppThemeProvider.colorsSystem.icon.active,
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 14.dp
                ),
            )
        }
    }
}