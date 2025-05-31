package info.javaway.wiseSpend.features.events.extensions

import info.javaway.wiseSpend.features.categories.models.Category
import info.javaway.wiseSpend.features.events.models.SpendEvent
import info.javaway.wiseSpend.features.events.models.SpendEventUI
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarLabel

fun SpendEvent.toUI(category: Category) = SpendEventUI(
    id = id,
    category = category,
    title = title,
    cost = cost
)

fun SpendEvent.toCalendarLabel(category: Category) = CalendarLabel(
    id = id, colorHex = category.colorHex, localDate = date
)