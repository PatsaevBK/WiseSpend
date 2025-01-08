package info.javaway.spend_sense.events.models

import db.events.EventTable
import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus

data class SpendEvent(
    val id: String,
    val categoryId: String,
    val title: String,
    val cost: Double,
    val date: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val note: String,
) {
    companion object {
        val NONE = SpendEvent(
            id = "",
            categoryId = "",
            title = "",
            cost = 0.0,
            date = LocalDate.now(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            note = ""
        )

        fun getStubs() = List(20) {
            NONE.copy(
                id = "$it",
                title = "event $it",
                cost = it.toDouble(),
                date = LocalDate.now().plus(it, DateTimeUnit.DAY),
            )
        }
    }
}

fun EventTable.toSpendEvent() = SpendEvent(
    id = id,
    categoryId = categoryId,
    title = title.orEmpty(),
    cost = cost,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt,
    note = note.orEmpty(),
)

fun SpendEvent.toDb() = EventTable(
    id = id,
    categoryId = categoryId,
    title = title,
    cost = cost,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt,
    note = note
)
