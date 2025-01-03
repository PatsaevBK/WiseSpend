package info.javaway.spend_sense.events.list.compose

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import info.javaway.spend_sense.common.ui.atoms.FAB
import info.javaway.spend_sense.common.ui.atoms.RootBox
import info.javaway.spend_sense.common.ui.calendar.compose.CalendarColors
import info.javaway.spend_sense.common.ui.calendar.compose.DatePickerView
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.di.DatePickerSingleQualifier
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.events.creation.compose.CreateEventView
import info.javaway.spend_sense.events.list.EventsScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxScope.EventsScreen(
    eventsScreenViewModel: EventsScreenViewModel,
    isBottomBarVisible: MutableState<Boolean>,
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val state by eventsScreenViewModel.state.collectAsState()

    ModalBottomSheetLayout(
        sheetContent = {
            CreateEventView(
                isExpand = modalBottomSheetState.currentValue == ModalBottomSheetValue.Expanded,
                selectedDay = state.selectedDay,
                viewModel = getKoinInstance(),
                isBottomBarVisible = isBottomBarVisible,
            ) {
                eventsScreenViewModel.createEvent(it)
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
                    labels = state.calendarLabels,
                    selectDayListener = eventsScreenViewModel::selectDay
                )

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.eventsByDay) {
                        SpendEventItem(it)
                    }
                }
            }
        }

        FAB {
            scope.launch {
                isBottomBarVisible.value = false
                modalBottomSheetState.show()
            }
        }
    }
}