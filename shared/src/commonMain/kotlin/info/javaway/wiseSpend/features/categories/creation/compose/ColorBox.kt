package info.javaway.wiseSpend.features.categories.creation.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorBox(
    rColor: Float,
    gColor: Float,
    bColor: Float,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .border(
                color = Color(rColor, gColor, bColor),
                width = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        content()
    }
}