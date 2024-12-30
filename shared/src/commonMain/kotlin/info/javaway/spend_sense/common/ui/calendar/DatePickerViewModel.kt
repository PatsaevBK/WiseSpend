package info.javaway.spend_sense.common.ui.calendar

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.common.ui.calendar.DatePickerContract.Event
import info.javaway.spend_sense.common.ui.calendar.DatePickerContract.State
import info.javaway.spend_sense.common.ui.calendar.extensions.next
import info.javaway.spend_sense.common.ui.calendar.extensions.prev
import info.javaway.spend_sense.common.ui.calendar.model.CalendarDay
import info.javaway.spend_sense.common.ui.calendar.model.CalendarLabel
import info.javaway.spend_sense.common.ui.calendar.model.CalendarMonth
import info.javaway.spend_sense.extensions.now
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

class DatePickerViewModel : BaseViewModel<State, Event>() {

    override fun initialState() = State.NONE

    init{
        activate()
    }

    fun selectDay(day: CalendarDay) {
        pushEvent(Event.SelectDay(day))
        updateState { copy(selectedDay = day) }
        updateWeeks()
    }

    fun prevMonth() {
        val prevMonth = state.value.calendarMonth.month.prev()
        var year = state.value.calendarMonth.year
        if (prevMonth == Month.DECEMBER) year -= 1
        updateMonthInState(year, prevMonth)
    }

    fun nextMonth() {
        val nextMonth = state.value.calendarMonth.month.next()
        var year = state.value.calendarMonth.year
        if (nextMonth == Month.JANUARY) year += 1
        updateMonthInState(year, nextMonth)
    }

    fun updateYear(year: Int) {
        if (year == state.value.calendarMonth.year) return
        val newLocalDate = LocalDate(year, state.value.calendarMonth.month, 1)
        val newCalendarMonth = CalendarMonth.fromDate(newLocalDate)
        updateState { copy(calendarMonth = newCalendarMonth) }
        updateWeeks()
    }


    fun updateLabels(labels: List<CalendarLabel>) {
        updateState { copy(labels = labels) }
        updateWeeks()
    }

    private fun activate() {
        updateState {
            copy(calendarMonth = CalendarMonth.fromDate(LocalDate.now()))
        }
        updateWeeks()
    }

    private fun updateMonthInState(year: Int, month: Month) {
        val calendarMonth = CalendarMonth(year, month)
        updateState { copy(calendarMonth = calendarMonth) }
        updateWeeks()
    }

    private fun updateWeeks() = viewModelScope.launch(Dispatchers.Default) {
        with(state.value) {
            val weeks = calendarMonth.getWeeks(firstDayIsMonday, selectedDay, labels)
            println("XXX weeks = $weeks")
            updateState { copy(weeks = weeks) }
        }
    }
}