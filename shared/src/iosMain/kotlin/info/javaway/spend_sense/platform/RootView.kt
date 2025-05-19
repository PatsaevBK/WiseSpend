package info.javaway.spend_sense.platform

import androidx.compose.ui.window.ComposeUIViewController
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.root.RootComponent
import info.javaway.spend_sense.root.compose.RootScreen

fun mainViewController(rootComponent: RootComponent) =
    ComposeUIViewController(
        configure = { enforceStrictPlistSanityCheck = false }
    ) {
        RootScreen(rootComponent = rootComponent)
    }

fun getRootFactory(): RootComponent.Factory = getKoinInstance()