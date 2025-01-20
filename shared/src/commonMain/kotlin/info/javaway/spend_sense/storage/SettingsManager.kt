package info.javaway.spend_sense.storage

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsManager(private val settings: Settings) {

    val serverUrl = "http://192.168.1.121:1337"
    private val THEME_KEY = "themeIsDark"
    private val TOKEN_KEY = "TOKEN_KEY"

    var themeIsDark = settings[THEME_KEY, false]
        set(value) {
            _themeIsDarkFlow.update { value }
            settings.putBoolean(THEME_KEY, value)
        }

    private val _themeIsDarkFlow = MutableStateFlow(themeIsDark)
    val themeIsDarkFlow = _themeIsDarkFlow.asStateFlow()

    var token: String
        set(value) = settings.putString(TOKEN_KEY, value)
        get() = settings.getString(TOKEN_KEY, "")
}