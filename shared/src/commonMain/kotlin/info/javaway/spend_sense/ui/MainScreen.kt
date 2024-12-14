package info.javaway.spend_sense.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.javaway.spend_sense.platform.DeviceInfo

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.size(450.dp), contentAlignment = Alignment.Center) {
        Text(DeviceInfo().getSummary())
    }
}
