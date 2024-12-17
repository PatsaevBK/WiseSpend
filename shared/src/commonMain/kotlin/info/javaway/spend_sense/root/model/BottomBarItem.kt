package info.javaway.spend_sense.root.model

enum class BottomBarItem(
    val title: String,
    val appTab: AppTab,
) {
    EVENTS("Events", AppTab.Events),
    CATEGORIES("Categories", AppTab.Categories),
    SETTINGS("Settings", AppTab.Settings),
}