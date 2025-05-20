package info.javaway.wiseSpend.platform

import androidx.compose.ui.window.ComposeUIViewController
import info.javaway.wiseSpend.di.getKoinInstance
import info.javaway.wiseSpend.root.RootComponent
import info.javaway.wiseSpend.root.compose.RootScreen

fun mainViewController(rootComponent: RootComponent) =
    ComposeUIViewController(
        configure = { enforceStrictPlistSanityCheck = false }
    ) {
        RootScreen(rootComponent = rootComponent)
    }

fun getRootFactory(): RootComponent.Factory = getKoinInstance()