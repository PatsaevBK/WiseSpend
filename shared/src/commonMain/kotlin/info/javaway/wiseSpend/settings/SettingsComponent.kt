package info.javaway.wiseSpend.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.settings.child.auth.AuthComponent
import info.javaway.wiseSpend.settings.child.sync.SyncComponent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SettingsComponent {

    val model: StateFlow<SettingsContract.State>
    val effects: SharedFlow<SettingsContract.Effect>

    val stack: Value<ChildStack<*, Child>>

    fun switchTheme(isDark: Boolean)

    sealed interface Child {
        data class Auth(val component: AuthComponent) :Child
        data class Sync(val component: SyncComponent) : Child
    }

    interface Factory {
        fun create(componentContext: ComponentContext): SettingsComponent
    }
}