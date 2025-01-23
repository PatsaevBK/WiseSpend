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
    private val EMAIL_KEY = "EMAIL_KEY"

    var themeIsDark = settings[THEME_KEY, false]
        set(value) {
            _themeIsDarkFlow.update { value }
            settings.putBoolean(THEME_KEY, value)
        }

    private val _themeIsDarkFlow = MutableStateFlow(themeIsDark)
    val themeIsDarkFlow = _themeIsDarkFlow.asStateFlow()

    private val _tokenFlow = MutableStateFlow(token)
    val tokenFlow = _tokenFlow.asStateFlow()
    var token: String
        set(value) {
            _tokenFlow.update { value }
            settings.putString(TOKEN_KEY, value)
        }
        get() = settings.getString(TOKEN_KEY, "")

    private val _emailFlow = MutableStateFlow(email)
    val emailFlow = _emailFlow.asStateFlow()
    var email: String
        set(value) {
            settings.putString(EMAIL_KEY, value)
            _emailFlow.value = value
        }
        get() = settings.getString(EMAIL_KEY, "")

}