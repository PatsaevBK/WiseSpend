package info.javaway.wiseSpend.mocks

import info.javaway.wiseSpend.features.events.data.EventsRepository
import info.javaway.wiseSpend.features.events.models.SpendEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

internal class EventsRepositoryMock : EventsRepository {
    override fun getAllFlow(): Flow<List<SpendEvent>> {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<SpendEvent> {
        TODO("Not yet implemented")
    }

    override fun getById(id: String): SpendEvent? {
        TODO("Not yet implemented")
    }

    override fun insertAll(events: List<SpendEvent>) {
        TODO("Not yet implemented")
    }

    var createSpendEvent: SpendEvent? = null
    var isCreateInvoked = false
    override fun create(spendEvent: SpendEvent) {
        isCreateInvoked = true
        createSpendEvent = spendEvent
    }

    var updateId: String? = null
    var isUpdateInvoked = false
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
        isUpdateInvoked = true
        updateId = id
    }
}