package info.javaway.wiseSpend.root.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import info.javaway.wiseSpend.features.accounts.list.compose.AccountsListScreen
import info.javaway.wiseSpend.features.categories.list.compose.CategoriesScreen
import info.javaway.wiseSpend.features.events.list.compose.EventsScreen
import info.javaway.wiseSpend.features.settings.compose.SettingScreen
import info.javaway.wiseSpend.root.RootComponent
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppNavigationBar
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import info.javaway.wiseSpend.uiLibrary.ui.theme.Theme

@Composable
fun RootScreen(
    rootComponent: RootComponent,
    modifier: Modifier = Modifier,
) {
    val state by rootComponent.state.collectAsState()

    Theme(
        themeIsDark = state.themeIsDark,
        appPrefs = state.appPrefs
    ) {
        Scaffold(
            modifier = modifier,
            bottomBar = {
                AppNavigationBar(rootComponent)
            },
            contentColor = AppThemeProvider.colorsSystem.fill.primary,
        ) { paddingValues ->
            Children(stack = rootComponent.stack, modifier = Modifier.padding(paddingValues)) {
                when (val child = it.instance) {
                    is RootComponent.Child.Events -> EventsScreen(component = child.component)
                    is RootComponent.Child.Categories -> CategoriesScreen(component = child.component)
                    is RootComponent.Child.Accounts -> AccountsListScreen(component = child.component)
                    is RootComponent.Child.Settings -> SettingScreen(component = child.component)
                }
            }
        }
    }
}
