package info.javaway.spend_sense.storage

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

expect class AppSettings() {
    val settings: Settings
}

object SettingsManager {
    private val appSettings = AppSettings()

    private const val THEME_KEY = "themeIsDark"

    var themeIsDark = appSettings.settings[THEME_KEY, false]
        set(value) {
            _themeIsDarkFlow.update { value }
            appSettings.settings.putBoolean(THEME_KEY, value)
        }

    private val _themeIsDarkFlow = MutableStateFlow(themeIsDark)
    val themeIsDarkFlow = _themeIsDarkFlow.asStateFlow()
}