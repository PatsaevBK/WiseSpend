package info.javaway.wiseSpend.features.events.data

import info.javaway.wiseSpend.features.events.models.SpendEvent

class EventsRepositoryImpl(
    private val dao: EventDao
): EventsRepository {

    override fun getAllFlow() = dao.getAllFlow()
    override fun getAll() = dao.getAll()
    override fun getById(id: String) = dao.get(id)

    override fun insertAll(events: List<SpendEvent>) = dao.insertAll(events)
    override fun create(spendEvent: SpendEvent) = dao.insert(spendEvent)
}