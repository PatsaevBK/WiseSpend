package info.javaway.spend_sense.settings

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingsViewModel: BaseViewModel<SettingsContract.State, Nothing>() {

    init {
        SettingsManager.themeIsDarkFlow.onEach {
            updateState {
                copy(themeIsDark = it)
            }
        }.launchIn(viewModelScope)

    }

    override fun initialState(): SettingsContract.State = SettingsContract.State.NONE

    fun switchTheme(isDark: Boolean) {
        SettingsManager.themeIsDark = isDark
    }

}