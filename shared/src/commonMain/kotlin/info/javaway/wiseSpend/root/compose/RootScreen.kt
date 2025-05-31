package info.javaway.wiseSpend.root.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.features.categories.list.compose.CategoriesScreen
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import info.javaway.wiseSpend.uiLibrary.ui.theme.Theme
import info.javaway.wiseSpend.features.events.list.compose.EventsScreen
import info.javaway.wiseSpend.root.RootComponent
import info.javaway.wiseSpend.root.model.BottomBarItem
import info.javaway.wiseSpend.features.settings.compose.SettingScreen
import org.jetbrains.compose.resources.stringResource

@Composable
fun RootScreen(
    rootComponent: RootComponent,
    modifier: Modifier = Modifier,
) {
    val state by rootComponent.state.collectAsState()
    isSystemInDarkTheme()

    Theme(
        themeIsDark = isSystemInDarkTheme(),
        appPrefs = state.appPrefs
    ) {

        Scaffold(
            modifier = modifier,
            bottomBar = {
                NavigationBar(
                    containerColor = AppThemeProvider.colors.surface,
                ) {
                    NavigationButtons(component = rootComponent)
                }
            },
            backgroundColor = AppThemeProvider.colors.background
        ) { paddingValues ->
            Children(stack = rootComponent.stack, modifier = Modifier.padding(paddingValues)) {
                when (val child = it.instance) {
                    is RootComponent.Child.Events -> EventsScreen(component = child.component)
                    is RootComponent.Child.Categories -> CategoriesScreen(component = child.component)
                    is RootComponent.Child.Settings -> SettingScreen(component = child.component)
                }
            }
        }
    }
}

@Composable
private fun RowScope.NavigationButtons(
    component: RootComponent,
) {
    val stack by component.stack.subscribeAsState()
    val isActive = stack.active.instance

    BottomBarItem.entries.forEach { bottomBarItem ->
        val isSelected = when (bottomBarItem) {
            BottomBarItem.EVENTS -> isActive is RootComponent.Child.Events
            BottomBarItem.CATEGORIES -> isActive is RootComponent.Child.Categories
            BottomBarItem.SETTINGS -> isActive is RootComponent.Child.Settings
        }
        val onClick = when (bottomBarItem) {
            BottomBarItem.EVENTS -> component::onEventsClick
            BottomBarItem.CATEGORIES -> component::onCategoriesClick
            BottomBarItem.SETTINGS -> component::onSettingsClick
        }
        val label = stringResource(bottomBarItem.title)

        NavigationBarItem(
            selected = isSelected,
            icon = { Icon(imageVector = bottomBarItem.image, contentDescription = label) },
            label = { Text(text = label, color = AppThemeProvider.colors.onSurface) },
            onClick = onClick
        )
    }
}
