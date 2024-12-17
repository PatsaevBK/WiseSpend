package info.javaway.spend_sense.root.model

import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.common.ui.AppPrefs

interface RootContract {
    data class State(
        val themeIsDark: Boolean,
        val firstDayIsMonday: Boolean,
        val selectedTab: AppTab
    ) : BaseViewState {

        val appPrefs: AppPrefs
            get() = AppPrefs(firstDayIsMonday)

        companion object {
            val NONE = State(
                themeIsDark = true,
                firstDayIsMonday = true,
                selectedTab = AppTab.Events
            )
        }
    }
}