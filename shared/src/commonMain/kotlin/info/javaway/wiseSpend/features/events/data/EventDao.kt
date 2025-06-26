package info.javaway.wiseSpend.features.events.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import db.events.EventTable
import info.javaway.wiseSpend.db.AppDb
import info.javaway.wiseSpend.features.events.models.SpendEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.coroutines.CoroutineContext

class EventDao(
    db: AppDb,
    private val coroutineContext: CoroutineContext,
) {
    private val eventQueries = db.eventTableQueries

    fun getAll(): List<SpendEvent> = eventQueries
        .getAll()
        .executeAsList()
        .map { it.toSpendEvent() }

    fun getAllFlow(): Flow<List<SpendEvent>> = eventQueries
        .getAll()
        .asFlow()
        .mapToList(coroutineContext)
        .map { categories -> categories.map(EventTable::toSpendEvent) }

    fun get(id: String) = eventQueries
        .get(id)
        .executeAsOneOrNull()
        ?.toSpendEvent()

    fun insert(spendEvent: SpendEvent) = eventQueries
        .insert(spendEvent.toDb())

    fun update(
        id: String,
        categoryId: String,
        accountId: String,
        title: String,
        cost: Double,
        date: LocalDate,
        updatedAt: LocalDateTime,
        note: String
    ) = eventQueries.update(
        categoryId = categoryId,
        accountId = accountId,
        title = title,
        cost = cost,
        date = date,
        note = note,
        updatedAt = updatedAt,
        id = id
    )

    fun insertAll(spendEvents: List<SpendEvent>) = eventQueries
        .transaction {
            spendEvents.forEach { eventQueries.insert(it.toDb()) }
        }

    fun delete(id: String) = eventQueries
        .delete(id)
}