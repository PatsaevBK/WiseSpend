package info.javaway.wiseSpend.features.events.data

import info.javaway.wiseSpend.features.events.models.SpendEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class EventsRepositoryImpl(
    private val dao: EventDao
): EventsRepository {

    override fun getAllFlow() = dao.getAllFlow()
        .flowOn(Dispatchers.IO)

    override suspend fun getAll() =
        withContext(Dispatchers.IO) {
            dao.getAll()
        }

    override suspend fun getById(id: String) =
        withContext(Dispatchers.IO) {
            dao.get(id)
        }

    override suspend fun insertAll(events: List<SpendEvent>) =
        withContext(Dispatchers.IO) {
            dao.insertAll(events)
        }
    override suspend fun create(spendEvent: SpendEvent) =
        withContext(Dispatchers.IO) {
            dao.insert(spendEvent)
        }

    override suspend fun update(
        id: String,
        categoryId: String,
        accountId: String,
        title: String,
        cost: Double,
        date: LocalDate,
        updatedAt: LocalDateTime,
        note: String
    ) {
        withContext(Dispatchers.IO) {
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
}