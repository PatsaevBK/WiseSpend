package info.javaway.spend_sense.settings.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.javaway.spend_sense.settings.SettingsViewModel

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel,
) {
    val state by settingsViewModel.state.collectAsState()
    Box(modifier = modifier.size(450.dp), contentAlignment = Alignment.Center) {
        Column {
            Text(state.deviceInfo)

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Dark theme", modifier = Modifier.weight(1f))
                Checkbox(
                    checked = state.themeIsDark,
                    onCheckedChange = { settingsViewModel.switchTheme(it) }
                )
            }
        }
    }
}