package info.javaway.spend_sense.root.model

import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.common.ui.theme.AppPrefs

interface RootContract {
    data class State(
        val themeIsDark: Boolean,
        val firstDayIsMonday: Boolean,
    ) : BaseViewState {

        val appPrefs: AppPrefs
            get() = AppPrefs(firstDayIsMonday)

        companion object {
            val NONE = State(
                themeIsDark = true,
                firstDayIsMonday = true,
            )
        }
    }
}