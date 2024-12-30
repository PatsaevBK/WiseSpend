package info.javaway.spend_sense.common.ui.calendar.model

data class CalendarWeek(
    val days: List<CalendarDay>
){
    companion object{
        private const val COUNT_WEEKS = 6
        private const val COUNT_DAYS = 7
        val PLACEHOLDER = List(COUNT_WEEKS) { CalendarWeek(days = List(COUNT_DAYS) { CalendarDay.NONE.copy(isStub = false) }) }
    }
}