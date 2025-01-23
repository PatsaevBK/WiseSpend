package info.javaway.spend_sense.settings

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.models.toApi
import info.javaway.spend_sense.events.EventsRepository
import info.javaway.spend_sense.events.models.toApi
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.platform.DeviceInfo
import info.javaway.spend_sense.settings.SettingsContract.*
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    deviceInfo: DeviceInfo,
    private val settingsManager: SettingsManager,
    private val categoriesRepository: CategoriesRepository,
    private val eventRepository: EventsRepository,
    private val api: AppApi
) : BaseViewModel<State, Event>() {

    init {
        bindToTheme()
        bindToEmail()
        bindToToken()
        updateState { copy(deviceInfo = deviceInfo.getSummary()) }
        updateState { copy(email = settingsManager.email) }
    }

    private fun bindToTheme() {
        settingsManager.themeIsDarkFlow.onEach {
            updateState {
                copy(themeIsDark = it)
            }
        }.launchIn(viewModelScope)
    }

    override fun initialState(): State = State.NONE

    fun switchTheme(isDark: Boolean) {
        settingsManager.themeIsDark = isDark
    }

    fun sync(): Job {
        return viewModelScope.launch(
            CoroutineExceptionHandler { _, t ->
                appLog(t.stackTraceToString())
                pushEvent(Event.Error(t.message.orEmpty()))
            }
        ) {
            updateState { copy(isLoading = true) }
            delay(3000)
            syncCategories()
            syncEvens()
            pushEvent(Event.DataSynced)
            updateState { copy(isLoading = false) }
        }
    }

    fun logout() {
        settingsManager.email = ""
        settingsManager.token = ""
    }

    private fun syncCategories() {
        viewModelScope.launch {
            categoriesRepository.getAllFlow().collect { categories ->
                val success = api.syncCategories(categories = categories.map { it.toApi() }).status.isSuccess()
                if (!success) pushEvent(Event.Error("Failed to sync categories"))
            }
        }
    }

    private fun syncEvens() {
        viewModelScope.launch {
            eventRepository.getAllFlow().collect { spendEvents ->
                val success = api.syncEvents(spendEvents.map { it.toApi() }).status.isSuccess()
                if (!success) pushEvent(Event.Error("Failed to sync events"))
            }
        }
    }

    private fun bindToEmail() {
        settingsManager.emailFlow.onEach { email ->
            updateState { copy(email = email) }
        }.launchIn(viewModelScope)
    }

    private fun bindToToken() {
        settingsManager.tokenFlow.onEach { token ->
            updateState { copy(isAuth = token.isNotBlank()) }
        }.launchIn(viewModelScope)
    }
}