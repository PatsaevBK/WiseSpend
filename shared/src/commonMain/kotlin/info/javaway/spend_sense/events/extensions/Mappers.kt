package info.javaway.spend_sense.events.extensions

import info.javaway.spend_sense.categories.models.Category
import info.javaway.spend_sense.common.ui.calendar.model.CalendarLabel
import info.javaway.spend_sense.events.models.SpendEvent
import info.javaway.spend_sense.events.models.SpendEventUI

fun SpendEvent.toUI(category: Category) = SpendEventUI(
    id = id,
    category = category,
    title = title,
    cost = cost
)

fun SpendEvent.toCalendarLabel(category: Category) = CalendarLabel(
    id = id, colorHex = category.colorHex, localDate = date
)