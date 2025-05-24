package info.javaway.wiseSpend.features.events

import info.javaway.wiseSpend.features.events.models.EventDao
import info.javaway.wiseSpend.features.events.models.SpendEvent

class EventsRepository(
    private val dao: EventDao
) {

    fun getAllFlow() = dao.getAllFlow()
    fun getAll() = dao.getAll()

    suspend fun insertAll(events: List<SpendEvent>) = dao.insertAll(events)
    suspend fun create(spendEvent: SpendEvent) = dao.insert(spendEvent)
}