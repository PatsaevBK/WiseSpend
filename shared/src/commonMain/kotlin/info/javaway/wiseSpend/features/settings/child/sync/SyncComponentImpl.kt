package info.javaway.wiseSpend.features.settings.child.sync

import com.arkivanov.decompose.ComponentContext
import info.javaway.wiseSpend.extensions.appLog
import info.javaway.wiseSpend.extensions.componentScope
import info.javaway.wiseSpend.features.accounts.data.AccountRepository
import info.javaway.wiseSpend.features.accounts.data.toAccount
import info.javaway.wiseSpend.features.accounts.data.toApi
import info.javaway.wiseSpend.features.accounts.models.AccountApi
import info.javaway.wiseSpend.features.categories.data.CategoriesRepository
import info.javaway.wiseSpend.features.categories.data.toApi
import info.javaway.wiseSpend.features.categories.data.toEntity
import info.javaway.wiseSpend.features.categories.models.CategoryApi
import info.javaway.wiseSpend.features.events.data.EventsRepository
import info.javaway.wiseSpend.features.events.data.toApi
import info.javaway.wiseSpend.features.events.data.toEvent
import info.javaway.wiseSpend.features.events.models.SpendEventApi
import info.javaway.wiseSpend.features.settings.child.sync.SyncComponent.Output
import info.javaway.wiseSpend.features.settings.child.sync.model.SyncContract
import info.javaway.wiseSpend.network.AppApi
import info.javaway.wiseSpend.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SyncComponentImpl(
    componentContext: ComponentContext,
    private val settingsManager: SettingsManager,
    private val categoriesRepository: CategoriesRepository,
    private val eventsRepository: EventsRepository,
    private val accountRepository: AccountRepository,
    private val api: AppApi,
    private val onOutput : (Output) -> Unit,
) : SyncComponent, ComponentContext by componentContext {

    private val scope = componentScope()

    private val _model = MutableStateFlow(SyncContract.State(email = settingsManager.email))
    override val model: StateFlow<SyncContract.State> = _model.asStateFlow()

    override fun sync() {
        scope.launch(
            CoroutineExceptionHandler { _, t ->
                appLog(t.stackTraceToString())
                onOutput(Output.Error(t.message.orEmpty()))
            }
        ) {
            _model.update { it.copy(isLoading = true) }
            delay(3000)
            syncAppData()
            onOutput(Output.DataSynced)
            _model.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun syncAppData() {
        if (!syncAccounts().isSuccess()) return
        if (!syncEvents().isSuccess()) return
        if (!syncCategories().isSuccess()) return
    }

    private suspend fun syncCategories(): HttpStatusCode {
        val apiCategories = categoriesRepository.getAll().map { it.toApi() }
        val categoriesSyncResponse = api.syncCategories(apiCategories)
        if (categoriesSyncResponse.status.isSuccess()) {
            val remoteCategories = categoriesSyncResponse.body<List<CategoryApi>>()
            categoriesRepository.insertAll(remoteCategories.map(CategoryApi::toEntity))
        }
        return categoriesSyncResponse.status
    }

    private suspend fun syncEvents(): HttpStatusCode {
        val apiEvents = eventsRepository.getAll().map { it.toApi() }
        val eventsSyncResponse = api.syncEvents(apiEvents)
        if (eventsSyncResponse.status.isSuccess()) {
            val remoteEvents = eventsSyncResponse.body<List<SpendEventApi>>()
            eventsRepository.insertAll(remoteEvents.map(SpendEventApi::toEvent))
        }
        return eventsSyncResponse.status
    }

    private suspend fun syncAccounts(): HttpStatusCode {
        val apiEvents = accountRepository.getAll().map { it.toApi() }
        val accountsSyncResponse = api.syncAccounts(apiEvents)
        if (accountsSyncResponse.status.isSuccess()) {
            val remoteEvents = accountsSyncResponse.body<List<AccountApi>>()
            accountRepository.insertAll(remoteEvents.map(AccountApi::toAccount))
        }
        return accountsSyncResponse.status
    }

    override fun logout() {
        settingsManager.email = ""
        settingsManager.token = ""
    }

    class Factory(
        private val settingsManager: SettingsManager,
        private val categoriesRepository: CategoriesRepository,
        private val eventsRepository: EventsRepository,
        private val accountRepository: AccountRepository,
        private val api: AppApi,
    ): SyncComponent.Factory {

        override fun create(componentContext: ComponentContext, onOutput : (Output) -> Unit): SyncComponent {
            return SyncComponentImpl(
                componentContext = componentContext,
                settingsManager = settingsManager,
                categoriesRepository = categoriesRepository,
                eventsRepository = eventsRepository,
                accountRepository = accountRepository,
                api = api,
                onOutput = onOutput
            )
        }
    }
}