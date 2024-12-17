package info.javaway.spend_sense.root

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RootViewModel: BaseViewModel<RootContract.State, Nothing>() {

    init {
        SettingsManager.themeIsDarkFlow.onEach {
            updateState { copy(themeIsDark = it) }
        }.launchIn(viewModelScope)
    }

    override fun initialState(): RootContract.State = RootContract.State.NONE
}