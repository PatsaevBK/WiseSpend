package info.javaway.wiseSpend.root.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.accounts
import wisespend.shared.generated.resources.categories
import wisespend.shared.generated.resources.events
import wisespend.shared.generated.resources.settings

enum class BottomBarItem(
    val title: StringResource,
    val image: ImageVector
) {
    EVENTS(Res.string.events, Icons.Rounded.CalendarMonth),
    CATEGORIES(Res.string.categories, Icons.Rounded.Folder),
    ACCOUNTS(Res.string.accounts, Icons.Outlined.Wallet),
    SETTINGS(Res.string.settings, Icons.Rounded.Settings),
}