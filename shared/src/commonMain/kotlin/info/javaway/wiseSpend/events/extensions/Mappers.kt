package info.javaway.wiseSpend.events.extensions

import info.javaway.wiseSpend.categories.models.Category
import info.javaway.wiseSpend.common.ui.calendar.model.CalendarLabel
import info.javaway.wiseSpend.events.models.SpendEvent
import info.javaway.wiseSpend.events.models.SpendEventUI

fun SpendEvent.toUI(category: Category) = SpendEventUI(
    id = id,
    category = category,
    title = title,
    cost = cost
)

fun SpendEvent.toCalendarLabel(category: Category) = CalendarLabel(
    id = id, colorHex = category.colorHex, localDate = date
)