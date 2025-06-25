package info.javaway.wiseSpend.features.events.data

import info.javaway.wiseSpend.features.events.models.SpendEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

interface EventsRepository {
    fun getAllFlow(): Flow<List<SpendEvent>>
    fun getAll(): List<SpendEvent>
    fun getById(id: String): SpendEvent?

    fun insertAll(events: List<SpendEvent>)
    fun create(spendEvent: SpendEvent)
    fun update(
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