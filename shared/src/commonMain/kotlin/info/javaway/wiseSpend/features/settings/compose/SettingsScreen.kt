package info.javaway.wiseSpend.features.settings.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.features.settings.SettingsComponent
import info.javaway.wiseSpend.features.settings.child.auth.compose.AuthView
import info.javaway.wiseSpend.features.settings.child.sync.compose.SyncView
import info.javaway.wiseSpend.uiLibrary.ui.atoms.RootBox

@Composable
fun SettingScreen(
    component: SettingsComponent,
    modifier: Modifier = Modifier,
) {
    val model by component.model.collectAsState()
    val stack by component.stack.subscribeAsState()

    RootBox(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            when(val child = stack.active.instance){
                is SettingsComponent.Child.Auth -> AuthView(component = child.component)
                is SettingsComponent.Child.Sync -> SyncView(component = child.component)
            }

            ThemeSwitch(checked = model.themeIsDark, onSwitch = { component.switchTheme(it) } )

            DeviceInfo(model.deviceInfo)
        }
    }
}
