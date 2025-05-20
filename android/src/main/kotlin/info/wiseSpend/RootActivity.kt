package info.wiseSpend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import info.javaway.wiseSpend.root.RootComponent
import info.javaway.wiseSpend.root.compose.RootScreen
import org.koin.java.KoinJavaComponent.inject

class RootActivity : ComponentActivity() {

    private val rootComponentFactory by inject<RootComponent.Factory>(clazz = RootComponent.Factory::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentContext = defaultComponentContext()
        val rootComponent = rootComponentFactory.create(componentContext)
        setContent {
            RootScreen(rootComponent)
        }
    }
}