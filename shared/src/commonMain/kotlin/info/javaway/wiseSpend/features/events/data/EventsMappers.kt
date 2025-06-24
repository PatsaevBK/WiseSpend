package info.javaway.wiseSpend.features.events.data

import db.events.EventTable
import info.javaway.wiseSpend.extensions.now
import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.features.events.models.SpendEventApi
import info.javaway.wiseSpend.features.events.models.SpendEventUI
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarLabel
import kotlinx.datetime.LocalDateTime


fun EventTable.toSpendEvent() = SpendEvent(
    id = id,
    categoryId = categoryId,
    accountId = accountId,
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
    accountId = accountId,
    title = title,
    cost = cost,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt,
    note = note
)


fun SpendEvent.toApi() = SpendEventApi(
    id = id,
    categoryId = categoryId,
    accountId = accountId,
    title = title,
    cost = cost,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt,
    note = note
)

fun SpendEventApi.toEvent() = SpendEvent(
    id = id.orEmpty(),
    categoryId = categoryId.orEmpty(),
    accountId = accountId.orEmpty(),
    title = title.orEmpty(),
    cost = cost ?: 0.0,
    date = date ?: LocalDateTime.now().date,
    createdAt = createdAt ?: LocalDateTime.now(),
    updatedAt = updatedAt ?: LocalDateTime.now(),
    note = note.orEmpty()
)

fun SpendEvent.toUI(category: Category) = SpendEventUI(
    id = id,
    category = category,
    title = title,
    cost = cost
)

fun SpendEvent.toCalendarLabel(category: Category) = CalendarLabel(
    id = id, colorHex = category.colorHex, localDate = date
)