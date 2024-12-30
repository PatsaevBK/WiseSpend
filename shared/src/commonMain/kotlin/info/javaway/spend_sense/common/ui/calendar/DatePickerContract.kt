package info.javaway.spend_sense.common.ui.calendar

import info.javaway.spend_sense.base.BaseViewEvent
import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.common.ui.calendar.model.CalendarDay
import info.javaway.spend_sense.common.ui.calendar.model.CalendarLabel
import info.javaway.spend_sense.common.ui.calendar.model.CalendarMonth
import info.javaway.spend_sense.common.ui.calendar.model.CalendarWeek
import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.LocalDate

interface DatePickerContract {
    data class State(
        val weeks: List<CalendarWeek>,
        val selectedDay: CalendarDay?,
        val firstDayIsMonday: Boolean,
        val labels: List<CalendarLabel>,
        val calendarMonth: CalendarMonth,
    ) : BaseViewState {

        companion object {
            val NONE = State(
                weeks = CalendarWeek.PLACEHOLDER,
                selectedDay = null,
                firstDayIsMonday = true,
                labels = emptyList(),
                calendarMonth = CalendarMonth.fromDate(LocalDate.now())
            )
        }
    }

    sealed interface Event : BaseViewEvent {
        data class SelectDay(val day: CalendarDay) : Event
    }
}