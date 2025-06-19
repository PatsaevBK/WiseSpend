import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import info.javaway.wiseSpend.di.getKoinInstance
import info.javaway.wiseSpend.di.initKoin
import info.javaway.wiseSpend.root.RootComponent
import info.javaway.wiseSpend.root.compose.RootScreen

fun main() {
    initKoin()

    val lifecycle = LifecycleRegistry()
    val componentContext = DefaultComponentContext(lifecycle)
    val rootFactory = getKoinInstance<RootComponent.Factory>()
    val rootComponent = runOnUiThread { rootFactory.create(componentContext) }

    application {
        Window(
            onCloseRequest = { exitApplication() },
            state = rememberWindowState().apply { size = DpSize(1200.dp, 1200.dp) },
            alwaysOnTop = true,
            title = "SpendSense"
        ) {
            RootScreen(rootComponent)
        }
    }
}