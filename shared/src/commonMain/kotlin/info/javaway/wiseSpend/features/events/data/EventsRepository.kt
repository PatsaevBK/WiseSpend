package info.javaway.wiseSpend.features.events.data

import info.javaway.wiseSpend.features.events.models.SpendEvent
import kotlinx.coroutines.flow.Flow

interface EventsRepository {
    fun getAllFlow(): Flow<List<SpendEvent>>
    fun getAll(): List<SpendEvent>
    fun getById(id: String): SpendEvent?

    fun insertAll(events: List<SpendEvent>)
    fun create(spendEvent: SpendEvent)
}