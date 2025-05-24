package info.javaway.wiseSpend.features.settings

import info.javaway.wiseSpend.base.BaseViewEvent
import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.platform.DeviceInfo

interface SettingsContract {
    data class State(
        val deviceInfo: String,
        val themeIsDark: Boolean,
        val isAuth: Boolean,
        val isLoading: Boolean,
        val email: String
    ): BaseViewState {
        companion object {
            val NONE = State(
                deviceInfo = DeviceInfo().getSummary(),
                themeIsDark = false,
                isAuth = false,
                isLoading = false,
                email = ""
            )
        }
    }

    sealed interface Effect: BaseViewEvent {
        data class Error(val msg: String) : Effect
        data object DataSynced: Effect
    }
}