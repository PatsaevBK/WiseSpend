package info.javaway.wiseSpend.network

import info.javaway.wiseSpend.features.accounts.models.AccountApi
import info.javaway.wiseSpend.features.categories.models.CategoryApi
import info.javaway.wiseSpend.features.events.models.SpendEventApi
import info.javaway.wiseSpend.features.settings.child.auth.child.register.model.RegisterRequest
import info.javaway.wiseSpend.features.settings.child.auth.child.signIn.model.SignInRequest
import info.javaway.wiseSpend.storage.SettingsManager
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class AppApi(
    private val httpClient: HttpClient,
    private val settingsManager: SettingsManager
) {

    suspend fun register(req: RegisterRequest) =
        withContext(Dispatchers.IO) {
            httpClient.post("${settingsManager.serverUrl}/api/auth/local/register") {
                contentType(ContentType.Application.Json)
                setBody(req)
            }
        }

    suspend fun signIn(req: SignInRequest) =
        withContext(Dispatchers.IO) {
            httpClient.post("${settingsManager.serverUrl}/api/auth/local") {
                contentType(ContentType.Application.Json)
                setBody(req)
            }
        }

    suspend fun syncCategories(categories: List<CategoryApi>) =
        withContext(Dispatchers.IO) {
            httpClient.post("${settingsManager.serverUrl}/api/categories/sync") {
                contentType(ContentType.Application.Json)
                bearerAuth(settingsManager.token)
                setBody(categories)
            }
        }

    suspend fun syncEvents(events: List<SpendEventApi>) =
        withContext(Dispatchers.IO) {
            httpClient.post("${settingsManager.serverUrl}/api/spend-events/sync") {
                contentType(ContentType.Application.Json)
                bearerAuth(settingsManager.token)
                setBody(events)
            }
        }

    suspend fun syncAccounts(accounts: List<AccountApi>) =
        withContext(Dispatchers.IO) {
            httpClient.post("${settingsManager.serverUrl}/api/accounts/sync") {
                contentType(ContentType.Application.Json)
                bearerAuth(settingsManager.token)
                setBody(accounts)
            }
        }
}