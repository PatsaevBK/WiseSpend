package info.javaway.wiseSpend.features.settings.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppCard
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider


@Composable
fun DeviceInfo(
    deviceInfo: String,
) {
    AppCard {
        Text(
            text = deviceInfo,
            color = AppThemeProvider.colors.onBackground,
        )
    }
}