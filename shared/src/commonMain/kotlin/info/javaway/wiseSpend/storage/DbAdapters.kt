package info.javaway.wiseSpend.storage

import app.cash.sqldelight.ColumnAdapter
import db.accounts.AccountTable
import db.categories.CategoryTable
import db.events.EventTable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

object DbAdapters {
    val categoryTableAdapter = CategoryTable.Adapter(
        createdAtAdapter = LocalDateTimeAdapter,
        updatedAtAdapter = LocalDateTimeAdapter,
    )

    val eventTableAdapter = EventTable.Adapter(
        dateAdapter = LocalDateAdapter,
        createdAtAdapter = LocalDateTimeAdapter,
        updatedAtAdapter = LocalDateTimeAdapter
    )

    val accountTableAdapter = AccountTable.Adapter(
        createdAtAdapter = LocalDateTimeAdapter,
        updatedAtAdapter = LocalDateTimeAdapter,
    )
}

object LocalDateTimeAdapter : ColumnAdapter<LocalDateTime, String> {
    override fun decode(databaseValue: String): LocalDateTime = LocalDateTime.parse(databaseValue)

    override fun encode(value: LocalDateTime): String = value.toString()
}

object LocalDateAdapter : ColumnAdapter<LocalDate, String> {
    override fun decode(databaseValue: String): LocalDate = LocalDate.parse(databaseValue)

    override fun encode(value: LocalDate): String = value.toString()
}