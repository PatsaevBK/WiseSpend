package info.javaway.spend_sense.settings.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.settings.SettingsViewModel
import info.javaway.spend_sense.settings.auth.compose.AuthView
import info.javaway.spend_sense.settings.sync.SyncView
import org.jetbrains.compose.resources.stringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.dark_theme

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel,
) {
    val state by settingsViewModel.state.collectAsState()

    Box(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Column {

            if (state.isAuth) {
                SyncView(
                    email = state.email,
                    isLoading = state.isLoading,
                    syncListener = { settingsViewModel.sync() },
                    logoutListener = { settingsViewModel.logout() }
                )
            } else {
                AuthView {
                    appLog("XXX success logging after register")
                    settingsViewModel.sync()
                }
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .background(AppThemeProvider.colors.surface, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(Res.string.dark_theme),
                    modifier = Modifier.weight(1f),
                    color = AppThemeProvider.colors.onSurface
                )
                Switch(
                    checked = state.themeIsDark,
                    onCheckedChange = { settingsViewModel.switchTheme(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppThemeProvider.colors.accent,
                        checkedTrackColor = AppThemeProvider.colors.onSurface,
                        uncheckedTrackColor = AppThemeProvider.colors.accent
                    )
                )
            }

            DeviceInfo(state.deviceInfo)
        }
    }
}
