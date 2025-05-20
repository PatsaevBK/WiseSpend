package info.javaway.wiseSpend.events.list.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import info.javaway.wiseSpend.common.ui.atoms.FAB
import info.javaway.wiseSpend.common.ui.atoms.RootBox
import info.javaway.wiseSpend.common.ui.calendar.compose.CalendarColors
import info.javaway.wiseSpend.common.ui.calendar.compose.DatePickerView
import info.javaway.wiseSpend.common.ui.theme.AppThemeProvider
import info.javaway.wiseSpend.di.DatePickerSingleQualifier
import info.javaway.wiseSpend.di.getKoinInstance
import info.javaway.wiseSpend.events.list.EventsListComponent
import info.javaway.wiseSpend.events.creation.compose.CreateEventView
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(
    component: EventsListComponent,
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )
    val scope = rememberCoroutineScope()
    val model by component.model.collectAsState()

    ModalBottomSheetLayout(
        sheetContent = {
            CreateEventView(
                isExpand = modalBottomSheetState.currentValue == ModalBottomSheetValue.Expanded,
                selectedDay = model.selectedDay,
                viewModel = getKoinInstance(), // not correct
                modifier = Modifier.background(AppThemeProvider.colors.background)
            ) {
                component.createEvent(it)
                scope.launch {
                    modalBottomSheetState.hide()
                }
            }
        },
        sheetState = modalBottomSheetState
    ) {
        RootBox {
            Column {
                DatePickerView(
                    viewModel = getKoinInstance(DatePickerSingleQualifier),
                    colors = CalendarColors.default.copy(
                        colorSurface = AppThemeProvider.colors.surface,
                        colorOnSurface = AppThemeProvider.colors.onSurface,
                        colorAccent = AppThemeProvider.colors.accent
                    ),
                    firstDayIsMonday = true,
                    labels = model.calendarLabels,
                    selectDayListener = component::selectDay
                )

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(model.eventsByDay) {
                        SpendEventItem(it)
                    }
                }
            }
        }

        FAB {
            scope.launch {
                modalBottomSheetState.show()
            }
        }
    }
}