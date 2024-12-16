package info.javaway.spend_sense.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.settings.compose.SettingScreen
import info.javaway.spend_sense.settings.SettingsViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    SettingScreen(modifier = modifier, settingsViewModel = SettingsViewModel())
}
