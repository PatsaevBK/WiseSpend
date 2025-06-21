package info.javaway.wiseSpend.features.categories.list.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.uiLibrary.ui.atoms.ColorLabel
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@Composable
fun CategoryItem(category: Category, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .background(AppThemeProvider.colorsSystem.fill.card.grey, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { onClick.invoke() }
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(end = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = category.title,
                style = AppThemeProvider.typography.m.heading3,
                color = AppThemeProvider.colorsSystem.text.primary
            )

            if (category.description.isNotEmpty()) {
                Text(
                    text = category.description,
                    style = AppThemeProvider.typography.m.body,
                    color = AppThemeProvider.colorsSystem.text.secondary
                )
            }
        }

        ColorLabel(colorHex = category.colorHex)
    }
}