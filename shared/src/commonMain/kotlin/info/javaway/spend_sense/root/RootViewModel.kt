package info.javaway.spend_sense.root

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.root.model.AppTab
import info.javaway.spend_sense.root.model.RootContract
import info.javaway.spend_sense.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RootViewModel(
    settingsManager: SettingsManager
): BaseViewModel<RootContract.State, Nothing>() {

    init {
        settingsManager.themeIsDarkFlow.onEach {
            updateState { copy(themeIsDark = it) }
        }.launchIn(viewModelScope)
    }

    override fun initialState(): RootContract.State = RootContract.State.NONE

    fun handleClickOnTab(appTab: AppTab) {
        updateState { copy(selectedTab = appTab) }
    }
}