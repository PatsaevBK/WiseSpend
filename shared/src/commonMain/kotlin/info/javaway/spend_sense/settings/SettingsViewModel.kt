package info.javaway.spend_sense.settings

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.platform.DeviceInfo
import info.javaway.spend_sense.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingsViewModel(
    deviceInfo: DeviceInfo,
    private val settingsManager: SettingsManager,
): BaseViewModel<SettingsContract.State, Nothing>() {

    init {
        settingsManager.themeIsDarkFlow.onEach {
            updateState {
                copy(themeIsDark = it)
            }
        }.launchIn(viewModelScope)

        updateState { copy(deviceInfo = deviceInfo.getSummary()) }
    }

    override fun initialState(): SettingsContract.State = SettingsContract.State.NONE

    fun switchTheme(isDark: Boolean) {
        settingsManager.themeIsDark = isDark
    }

}