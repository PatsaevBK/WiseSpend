package info.javaway.wiseSpend.events.models

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import db.events.EventTable
import info.javaway.wiseSpend.db.AppDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    suspend fun insert(spendEvent: SpendEvent) = eventQueries
        .insert(spendEvent.toDb())

    suspend fun insertAll(spendEvents: List<SpendEvent>) = eventQueries
        .transaction {
            spendEvents.forEach { eventQueries.insert(it.toDb()) }
        }


    suspend fun delete(id: String) = eventQueries
        .delete(id)
}