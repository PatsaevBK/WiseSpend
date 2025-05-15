package info.javaway.spend_sense.root.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.categories
import spendsense.shared.generated.resources.events
import spendsense.shared.generated.resources.settings

enum class BottomBarItem(
    val title: StringResource,
    val image: ImageVector
) {
    EVENTS(Res.string.events, Icons.Rounded.CalendarMonth),
    CATEGORIES(Res.string.categories, Icons.Rounded.Folder),
    SETTINGS(Res.string.settings, Icons.Rounded.Settings),
}