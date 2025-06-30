package info.javaway.wiseSpend.features.events.data

import info.javaway.wiseSpend.features.events.models.SpendEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

interface EventsRepository {
    fun getAllFlow(): Flow<List<SpendEvent>>
    suspend fun getAll(): List<SpendEvent>
    suspend fun getById(id: String): SpendEvent?

    suspend fun insertAll(events: List<SpendEvent>)
    suspend fun create(spendEvent: SpendEvent)
    suspend fun update(
        id: String,
        categoryId: String,
        accountId: String,
        title: String,
        cost: Double,
        date: LocalDate,
        updatedAt: LocalDateTime,
        note: String
    )
}