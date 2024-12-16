package info.javaway.spend_sense.settings

import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.platform.DeviceInfo

interface SettingsContract {
    data class State(
        val deviceInfo: String,
        val themeIsDark: Boolean,
    ): BaseViewState {
        companion object {
            val NONE = State(
                deviceInfo = DeviceInfo().getSummary(),
                themeIsDark = false
            )
        }
    }
}