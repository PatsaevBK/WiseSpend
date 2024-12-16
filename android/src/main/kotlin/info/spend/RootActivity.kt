package info.spend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import info.javaway.spend_sense.greeting
import info.javaway.spend_sense.root.MainScreen


class RootActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        greeting()
        setContent {
            MainScreen()
        }
    }
}