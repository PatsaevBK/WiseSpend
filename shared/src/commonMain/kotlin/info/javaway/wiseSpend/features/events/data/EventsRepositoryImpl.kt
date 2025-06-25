package info.javaway.wiseSpend.features.events.data

import info.javaway.wiseSpend.features.events.models.SpendEvent
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class EventsRepositoryImpl(
    private val dao: EventDao
): EventsRepository {

    override fun getAllFlow() = dao.getAllFlow()
    override fun getAll() = dao.getAll()
    override fun getById(id: String) = dao.get(id)

    override fun insertAll(events: List<SpendEvent>) = dao.insertAll(events)
    override fun create(spendEvent: SpendEvent) {
        dao.insert(spendEvent)
    }

    override fun update(
        id: String,
        categoryId: String,
        accountId: String,
        title: String,
        cost: Double,
        date: LocalDate,
        updatedAt: LocalDateTime,
        note: String
    ) {
        dao.update(
            id = id,
            categoryId = categoryId,
            accountId = accountId,
            title = title,
            cost = cost,
            date = date,
            updatedAt = updatedAt,
            note = note
        )
    }
}