package info.javaway.spend_sense.platform

import androidx.compose.ui.window.ComposeUIViewController
import info.javaway.spend_sense.root.compose.RootScreen
//TODO configure = { enforceStrictPlistSanityCheck = false } понят ьпочему без этого падает iOS
fun mainViewController() = ComposeUIViewController(configure = { enforceStrictPlistSanityCheck = false }) {
    RootScreen()
}