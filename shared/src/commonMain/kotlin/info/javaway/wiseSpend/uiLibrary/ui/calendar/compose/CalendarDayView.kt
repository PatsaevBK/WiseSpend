package info.javaway.wiseSpend.uiLibrary.ui.calendar.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.extensions.fromHex
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarDay
import info.javaway.wiseSpend.uiLibrary.ui.calendar.model.CalendarLabel
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@Composable
fun RowScope.CalendarDayView(
    calendarDay: CalendarDay,
    selectDayListener: (CalendarDay) -> Unit,
) {

    if (calendarDay.isStub) {
        Spacer(modifier = Modifier.weight(1f))
    } else {
        DayContainer(
            calendarDay = calendarDay,
            selectDayListener = selectDayListener
        ) {
            DayText(calendarDay)
            CurrentDayLabel(calendarDay)
            GradientLabels(calendarDay.labels)
        }
    }
}

@Composable
fun RowScope.DayContainer(
    calendarDay: CalendarDay,
    selectDayListener: (CalendarDay) -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1.5f)
            .let {
                if (calendarDay.selectable) {
                    it.clickable {
                        selectDayListener(calendarDay)
                    }
                } else it
            }
    ) {
        content()
    }
}

@Composable
fun DayText(
    calendarDay: CalendarDay,
) {

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(2.dp)
            .run {
                if (calendarDay.isSelected) border(
                    width = 2.dp,
                    color = AppThemeProvider.colorsSystem.icon.secondary,
                    shape = RoundedCornerShape(8.dp)
                ) else this
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = calendarDay.number.toString(),
            style = if (calendarDay.isToday) AppThemeProvider.typography.l.heading3 else AppThemeProvider.typography.l.body,
            color = if (calendarDay.isToday) AppThemeProvider.colorsSystem.text.primary else AppThemeProvider.colorsSystem.text.secondary
        )
    }
}

@Composable
fun BoxScope.CurrentDayLabel(
    calendarDay: CalendarDay,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
            .height(4.dp)
            .align(Alignment.BottomCenter)
            .run {
                if (calendarDay.isToday) background(color = AppThemeProvider.colorsSystem.fill.active) else this
            }
    ) {}
}


@Composable
fun GradientLabels(labels: List<CalendarLabel>){
    if (labels.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .clip(CircleShape)
                .border(
                    4.dp,
                    brush = Brush.linearGradient(
                        colors = labels.mapToColors(),
                    ),
                    shape = CircleShape
                )

        ) { }
    }
}

@Composable
fun List<CalendarLabel>.mapToColors() = this.map {
    it.colorHex?.takeIf { it.isNotEmpty() }?.let { colorHex ->
        Color.fromHex(colorHex)
    } ?: LocalCalendarColors.current.colorAccent
}
    .toSet()
    .toList()
    .let { if (it.size == 1) { it + it } else it }