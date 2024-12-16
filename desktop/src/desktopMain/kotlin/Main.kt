import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import info.javaway.spend_sense.greeting
import info.javaway.spend_sense.root.MainScreen

fun main() {
    greeting()
    application {
        Window(
            onCloseRequest = { exitApplication() },
            state = rememberWindowState().apply { size = DpSize(200.dp, 200.dp) },
            title = "SpendSense"
        ) {
            MainScreen()
        }
    }
}