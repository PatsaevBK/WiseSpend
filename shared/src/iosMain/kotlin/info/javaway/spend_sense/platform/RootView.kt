package info.javaway.spend_sense.platform

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import info.javaway.spend_sense.root.RootViewModel
import info.javaway.spend_sense.root.compose.RootScreen

fun mainViewController() = ComposeUIViewController {
    val rootViewModel = remember { RootViewModel() }
    RootScreen(rootViewModel = rootViewModel)
}