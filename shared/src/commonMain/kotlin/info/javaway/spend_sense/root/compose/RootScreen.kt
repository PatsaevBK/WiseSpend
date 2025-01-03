package info.javaway.spend_sense.root.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.categories.compose.CategoriesScreen
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.common.ui.theme.Theme
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.events.list.compose.EventsScreen
import info.javaway.spend_sense.root.RootViewModel
import info.javaway.spend_sense.root.model.AppTab
import info.javaway.spend_sense.settings.SettingsViewModel
import info.javaway.spend_sense.settings.compose.SettingScreen

@Composable
fun RootScreen(
    modifier: Modifier = Modifier,
) {
    val rootViewModel = remember { getKoinInstance<RootViewModel>() }
    val state by rootViewModel.state.collectAsState()

    val isBottomBarVisible = remember { mutableStateOf(true) }

    Theme(
        themeIsDark = state.themeIsDark,
        appPrefs = state.appPrefs
    ) {
        Scaffold(
            modifier = modifier,
            bottomBar = {
                if (isBottomBarVisible.value) {
                    RootBottomBar(
                        modifier = Modifier.background(AppThemeProvider.colors.background),
                        selectedTab = state.selectedTab
                    ) { rootViewModel.handleClickOnTab(it) }
                }
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(AppThemeProvider.colors.background)
                    .padding(it)
            ) {
                RootNavigation(state.selectedTab, isBottomBarVisible)
            }
        }
    }
}

@Composable
fun BoxScope.RootNavigation(selectedTab: AppTab, isBottomBarVisible: MutableState<Boolean>) {
    when (selectedTab) {
        AppTab.Categories -> CategoriesScreen(getKoinInstance(), isBottomBarVisible)
        AppTab.Events -> EventsScreen(getKoinInstance(), isBottomBarVisible)
        AppTab.Settings -> SettingScreen(
            settingsViewModel = SettingsViewModel(
                getKoinInstance(),
                getKoinInstance()
            )
        )
    }
}
