package info.javaway.wiseSpend.uiLibrary.ui.calendar.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.uiLibrary.ui.calendar.DatePickerContract
import info.javaway.wiseSpend.uiLibrary.ui.calendar.DatePickerViewModel
import info.javaway.wiseSpend.uiLibrary.ui.calendar.extensions.fromSunday
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarDay
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarLabel
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarMonth
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarWeek
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.DayOfWeek

@Composable
fun DatePickerView(
    viewModel: DatePickerViewModel,
    firstDayIsMonday: Boolean = false,
    labels: List<CalendarLabel> = emptyList(),
    selectDayListener: (CalendarDay) -> Unit = {}
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(labels) {
        viewModel.updateLabels(labels)
    }

    LaunchedEffect(Unit){
        viewModel.events.onEach { event ->
            when (event) {
                is DatePickerContract.Event.SelectDay -> selectDayListener(event.day)
            }
        }.launchIn(this)
    }
    Column(
        modifier = Modifier
            .background(
                AppThemeProvider.colorsSystem.fill.card.grey,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        CalendarHeader(
            calendarMonth = state.calendarMonth,
            prevMonthListener = { viewModel.prevMonth() },
            nextMonthListener = { viewModel.nextMonth() },
            yearSelectListener = { viewModel.updateYear(it) }
        )

        Divider(color = AppThemeProvider.colorsSystem.separator.primary, thickness = 4.dp)

        CalendarDaysLabels(firstDayIsMonday = firstDayIsMonday)
        state.weeks.forEach { week ->
            WeekView(week = week) { day -> viewModel.selectDay(day) }
        }
    }
}


@Composable
fun CalendarHeader(
    calendarMonth: CalendarMonth,
    prevMonthListener: () -> Unit,
    nextMonthListener: () -> Unit,
    yearSelectListener: (Int) -> Unit,
) {
    var selectYearDialogIsShown by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            calendarMonth.year.toString(),
            style = AppThemeProvider.typography.l.body,
            color = AppThemeProvider.colorsSystem.text.secondary,
            modifier = Modifier.clickable { selectYearDialogIsShown = true }
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = calendarMonth.month.name.lowercase().capitalize(Locale.current),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            style = AppThemeProvider.typography.l.heading3,
            color = AppThemeProvider.colorsSystem.text.primary
        )

        Image(
            imageVector = Icons.Rounded.ChevronLeft,
            contentDescription = "back",
            colorFilter = ColorFilter.tint(AppThemeProvider.colorsSystem.icon.primary),
            modifier = Modifier
                .padding(end = 24.dp)
                .size(38.dp)
                .clickable {
                    prevMonthListener()
                }
        )

        Image(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = "next",
            colorFilter = ColorFilter.tint(AppThemeProvider.colorsSystem.icon.primary),
            modifier = Modifier
                .size(38.dp)
                .clickable { nextMonthListener() }
        )

        if (selectYearDialogIsShown) {
            YearPicker(
                initialYear = calendarMonth.year,
                onDismissRequest = { selectYearDialogIsShown = false },
                onYearSelectListener = yearSelectListener,
            )
        }
    }
}

@Composable
fun CalendarDaysLabels(
    firstDayIsMonday: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DayOfWeek.entries.toTypedArray()
            .let { if (firstDayIsMonday) it.toList() else it.fromSunday() }
            .forEach { dayOfWeek ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        dayOfWeek.name.take(2),
                        style = AppThemeProvider.typography.l.body,
                        color = AppThemeProvider.colorsSystem.text.primary
                    )
                }

            }
    }
}


@Composable
fun WeekView(
    week: CalendarWeek,
    selectDayListener: (CalendarDay) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        week.days.forEach { calendarDay ->
            CalendarDayView(
                calendarDay = calendarDay,
                selectDayListener = selectDayListener
            )
        }
    }
}

data class CalendarColors(
    val colorSurface: Color,
    val colorOnSurface: Color,
    val colorAccent: Color
) {
    companion object {
        val default = CalendarColors(
            colorSurface = Color(0xFF1C1C1C),
            colorOnSurface = Color(0xFFBFBFBF),
            colorAccent = Color(0xFFFFFFFF)
        )
    }
}

val LocalCalendarColors = staticCompositionLocalOf {
    CalendarColors.default
}