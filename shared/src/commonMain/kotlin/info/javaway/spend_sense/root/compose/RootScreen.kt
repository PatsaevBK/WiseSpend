package info.javaway.spend_sense.root.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.categories.compose.CategoriesScreen
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.common.ui.theme.Theme
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.events.common.EventsScreen
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
    Theme(
        themeIsDark = state.themeIsDark,
        appPrefs = state.appPrefs
    ) {
        Box(modifier = modifier.fillMaxSize().background(AppThemeProvider.colors.background)) {
            RootNavigation(state.selectedTab)
            RootBottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                selectedTab = state.selectedTab
            ) { rootViewModel.handleClickOnTab(it) }
        }
    }
}

@Composable
fun RootNavigation(selectedTab: AppTab) {
    when (selectedTab) {
        AppTab.Categories -> CategoriesScreen()
        AppTab.Events -> EventsScreen()
        AppTab.Settings -> SettingScreen(settingsViewModel = SettingsViewModel(getKoinInstance(), getKoinInstance()))
    }
}
