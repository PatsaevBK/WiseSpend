package info.spend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import info.javaway.spend_sense.greeting
import info.javaway.spend_sense.root.RootViewModel
import info.javaway.spend_sense.root.compose.RootScreen


class RootActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        greeting()
        val rootViewModel =  RootViewModel()
        setContent {
            RootScreen(rootViewModel = rootViewModel)
        }
    }
}