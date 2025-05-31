package info.javaway.wiseSpend.features.settings.compose

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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import info.javaway.wiseSpend.features.settings.SettingsComponent
import info.javaway.wiseSpend.features.settings.child.auth.compose.AuthView
import info.javaway.wiseSpend.features.settings.child.sync.compose.SyncView
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.dark_theme

@Composable
fun SettingScreen(
    component: SettingsComponent,
    modifier: Modifier = Modifier,
) {
    val model by component.model.collectAsState()
    val stack by component.stack.subscribeAsState()

    Box(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Column {

            when(val child = stack.active.instance){
                is SettingsComponent.Child.Auth -> AuthView(child.component)
                is SettingsComponent.Child.Sync -> SyncView(child.component)
            }

            Row(
                modifier = Modifier
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
                    checked = model.themeIsDark,
                    onCheckedChange = { component.switchTheme(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppThemeProvider.colors.accent,
                        checkedTrackColor = AppThemeProvider.colors.onSurface,
                        uncheckedTrackColor = AppThemeProvider.colors.accent
                    )
                )
            }

            DeviceInfo(model.deviceInfo)
        }
    }
}
