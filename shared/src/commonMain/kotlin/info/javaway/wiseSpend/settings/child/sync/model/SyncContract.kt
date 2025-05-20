package info.javaway.wiseSpend.settings.child.sync.model

interface SyncContract {
    data class State(
        val isLoading: Boolean = false,
        val email: String = ""
    )
}