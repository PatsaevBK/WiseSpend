package info.javaway.wiseSpend.network

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

class AppApi(
    private val httpClient: HttpClient,
    private val settingsManager: SettingsManager
) {

    suspend fun register(req: RegisterRequest) =
        httpClient.post("${settingsManager.serverUrl}/api/auth/local/register") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }

    suspend fun signIn(req: SignInRequest) =
        httpClient.post("${settingsManager.serverUrl}/api/auth/local") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }

    suspend fun syncCategories(categories: List<CategoryApi>) =
        httpClient.post("${settingsManager.serverUrl}/api/categories/sync") {
            contentType(ContentType.Application.Json)
            bearerAuth(settingsManager.token)
            setBody(categories)
        }

    suspend fun syncEvents(events: List<SpendEventApi>) =
        httpClient.post("${settingsManager.serverUrl}/api/spend-events/sync") {
            contentType(ContentType.Application.Json)
            bearerAuth(settingsManager.token)
            setBody(events)
        }
}