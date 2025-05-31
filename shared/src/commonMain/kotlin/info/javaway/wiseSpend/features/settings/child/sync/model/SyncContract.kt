package info.javaway.wiseSpend.features.settings.child.sync.model

interface SyncContract {
    data class State(
        val isLoading: Boolean = false,
        val email: String = ""
    )
}