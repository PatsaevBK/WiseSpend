package info.javaway.spend_sense.settings.child.sync

import com.arkivanov.decompose.ComponentContext
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.extensions.toApi
import info.javaway.spend_sense.categories.models.CategoryApi
import info.javaway.spend_sense.categories.models.toEntity
import info.javaway.spend_sense.events.EventsRepository
import info.javaway.spend_sense.events.extensions.componentScope
import info.javaway.spend_sense.events.models.SpendEventApi
import info.javaway.spend_sense.events.models.toApi
import info.javaway.spend_sense.events.models.toEntity
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.settings.child.sync.SyncComponent.Output
import info.javaway.spend_sense.settings.child.sync.model.SyncContract
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.client.call.body
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
            syncCategories()
            syncEvens()
            onOutput(Output.DataSynced)
            _model.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun syncCategories() {
        val apiCategories = categoriesRepository.getAll().map { it.toApi() }
        val categoriesSyncResponse = api.syncCategories(apiCategories)
        if (categoriesSyncResponse.status.isSuccess()) {
            val remoteCategories = categoriesSyncResponse.body<List<CategoryApi>>()
            categoriesRepository.insertAll(remoteCategories.map(CategoryApi::toEntity))
        }
    }

    private suspend fun syncEvens() {
        val apiEvents = eventsRepository.getAll().map { it.toApi() }
        val eventsSyncResponse = api.syncEvents(apiEvents)
        if (eventsSyncResponse.status.isSuccess()) {
            val remoteEvents = eventsSyncResponse.body<List<SpendEventApi>>()
            eventsRepository.insertAll(remoteEvents.map { it.toEntity() })
        }
    }

    override fun logout() {
        settingsManager.email = ""
        settingsManager.token = ""
    }

    class Factory(
        private val settingsManager: SettingsManager,
        private val categoriesRepository: CategoriesRepository,
        private val eventsRepository: EventsRepository,
        private val api: AppApi,
    ): SyncComponent.Factory {

        override fun create(componentContext: ComponentContext, onOutput : (Output) -> Unit): SyncComponent {
            return SyncComponentImpl(
                componentContext = componentContext,
                settingsManager = settingsManager,
                categoriesRepository = categoriesRepository,
                eventsRepository = eventsRepository,
                api = api,
                onOutput = onOutput
            )
        }
    }
}