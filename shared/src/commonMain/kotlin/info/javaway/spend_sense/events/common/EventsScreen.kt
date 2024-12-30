package info.javaway.spend_sense.events.common

import androidx.compose.runtime.Composable
import info.javaway.spend_sense.common.ui.calendar.compose.CalendarColors
import info.javaway.spend_sense.common.ui.calendar.compose.DatePickerView
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.di.getKoinInstance

@Composable
fun EventsScreen() {
    DatePickerView(
        viewModel = getKoinInstance(),
        colors = CalendarColors.default.copy(
            colorSurface = AppThemeProvider.colors.surface,
            colorOnSurface = AppThemeProvider.colors.onSurface,
            colorAccent = AppThemeProvider.colors.accent
        ),
        firstDayIsMonday = true,
        labels = emptyList(),
        selectDayListener = { }
    )
}