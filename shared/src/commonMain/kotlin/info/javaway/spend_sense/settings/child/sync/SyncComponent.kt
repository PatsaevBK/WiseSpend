package info.javaway.spend_sense.settings.child.sync

import com.arkivanov.decompose.ComponentContext
import info.javaway.spend_sense.settings.child.sync.model.SyncContract
import kotlinx.coroutines.flow.StateFlow

interface SyncComponent {

    val model: StateFlow<SyncContract.State>

    fun sync()
    fun logout()

    sealed interface Output {
        class Error(val string: String) : Output
        data object DataSynced : Output
    }

    interface Factory {
        fun create(componentContext: ComponentContext, onOutput : (Output) -> Unit): SyncComponent
    }
}