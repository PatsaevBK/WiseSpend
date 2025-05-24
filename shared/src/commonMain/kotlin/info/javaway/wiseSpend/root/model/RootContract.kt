package info.javaway.wiseSpend.root.model

import info.javaway.wiseSpend.base.BaseViewState
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppPrefs

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