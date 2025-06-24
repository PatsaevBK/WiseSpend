package info.javaway.wiseSpend.features.events.data

import info.javaway.wiseSpend.features.events.models.SpendEvent

class EventsRepository(
    private val dao: EventDao
) {

    fun getAllFlow() = dao.getAllFlow()
    fun getAll() = dao.getAll()
    fun getById(id: String) = dao.get(id)

    fun insertAll(events: List<SpendEvent>) = dao.insertAll(events)
    fun create(spendEvent: SpendEvent) = dao.insert(spendEvent)
}