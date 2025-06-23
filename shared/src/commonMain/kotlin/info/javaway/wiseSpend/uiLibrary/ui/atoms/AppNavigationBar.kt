package info.javaway.wiseSpend.uiLibrary.ui.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.root.RootComponent
import info.javaway.wiseSpend.root.model.BottomBarItem
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppNavigationBar(rootComponent: RootComponent, modifier: Modifier = Modifier.Companion) {
    Column(modifier = modifier) {
        HorizontalDivider(thickness = 1.dp, color = AppThemeProvider.colorsSystem.fill.default)
        NavigationBar(
            containerColor = AppThemeProvider.colorsSystem.fill.primary,
        ) {
            NavigationButtons(component = rootComponent)
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
            BottomBarItem.ACCOUNTS -> isActive is RootComponent.Child.Accounts
            BottomBarItem.SETTINGS -> isActive is RootComponent.Child.Settings
        }
        val onClick = when (bottomBarItem) {
            BottomBarItem.EVENTS -> component::onEventsClick
            BottomBarItem.CATEGORIES -> component::onCategoriesClick
            BottomBarItem.ACCOUNTS -> component::onAccountsClick
            BottomBarItem.SETTINGS -> component::onSettingsClick
        }
        val label = stringResource(bottomBarItem.title)

        NavigationBarItem(
            selected = isSelected,
            icon = { Icon(imageVector = bottomBarItem.image, contentDescription = label, tint = AppThemeProvider.colorsSystem.icon.primary) },
            label = { Text(text = label, color = AppThemeProvider.colorsSystem.text.primary, style = AppThemeProvider.typography.m.body) },
            colors = NavigationBarItemDefaults.colors().copy(
                selectedIndicatorColor = AppThemeProvider.colorsSystem.fill.card.grey,
            ),
            onClick = onClick
        )
    }
}