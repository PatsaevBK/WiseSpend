package info.javaway.wiseSpend.uiLibrary.ui.atoms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BoxScope.AppToast(
    message: String?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppThemeProvider.colorsSystem.fill.secondary,
    hide: () -> Unit
) {
    AnimatedVisibility(
        visible = message != null,
        modifier = modifier.align(Alignment.BottomCenter)
    ){
        LaunchedEffect(Unit){
            launch {
                delay(3000)
                hide()
            }
        }

        Text(
            text = message.orEmpty(),
            modifier = Modifier
                .padding(16.dp)
                .background(backgroundColor, RoundedCornerShape(16.dp))
                .padding(16.dp)
                .clickable { hide() }
                .zIndex(1.0f),
            color = AppThemeProvider.colorsSystem.text.primary,
            style = AppThemeProvider.typography.l.body,
        )
    }
}