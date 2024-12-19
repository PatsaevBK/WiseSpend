package info.javaway.spend_sense.root.model

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import info.javaway.spend_sense.MR

enum class BottomBarItem(
    val title: StringResource,
    val appTab: AppTab,
    val image: ImageResource
) {
    EVENTS(MR.strings.events, AppTab.Events, MR.images.icon_calendar),
    CATEGORIES(MR.strings.categories, AppTab.Categories, MR.images.icon_folder),
    SETTINGS(MR.strings.settings, AppTab.Settings, MR.images.icon_settings),
}