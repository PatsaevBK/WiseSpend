package info.javaway.spend_sense.root.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.common.ui.AppThemeProvider
import info.javaway.spend_sense.common.ui.Theme
import info.javaway.spend_sense.root.RootViewModel
import info.javaway.spend_sense.settings.compose.SettingScreen
import info.javaway.spend_sense.settings.SettingsViewModel

@Composable
fun RootScreen(
    modifier: Modifier = Modifier,
    rootViewModel: RootViewModel = RootViewModel()
) {
    val state by rootViewModel.state.collectAsState()
    Theme(
        themeIsDark = state.themeIsDark,
        appPrefs = state.appPrefs
    ) {
        Box(modifier = modifier.fillMaxSize().background(AppThemeProvider.colors.background)) {
            SettingScreen(modifier = modifier, settingsViewModel = SettingsViewModel())
        }
    }
}
