package info.javaway.spend_sense.categories.list.compose

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
import androidx.compose.ui.unit.sp
import info.javaway.spend_sense.categories.models.Category
import info.javaway.spend_sense.common.ui.atoms.ColorLabel
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider

@Composable
fun CategoryItem(category: Category, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .background(AppThemeProvider.colors.surface, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { onClick.invoke() }
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(end = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = category.title,
                fontSize = 22.sp,
                color = AppThemeProvider.colors.onSurface
            )

            if (category.description.isNotEmpty()) {
                Text(
                    text = category.description,
                    fontSize = 18.sp,
                    color = AppThemeProvider.colors.onSurface.copy(0.7f)
                )
            }
        }

        ColorLabel(colorHex = category.colorHex)
    }
}