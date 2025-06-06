package info.javaway.wiseSpend.features.events.list.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.uiLibrary.ui.atoms.FAB
import info.javaway.wiseSpend.uiLibrary.ui.atoms.RootBox
import info.javaway.wiseSpend.uiLibrary.ui.calendar.compose.CalendarColors
import info.javaway.wiseSpend.uiLibrary.ui.calendar.compose.DatePickerView
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import info.javaway.wiseSpend.di.DatePickerSingleQualifier
import info.javaway.wiseSpend.di.getKoinInstance
import info.javaway.wiseSpend.features.events.creation.compose.CreateEventView
import info.javaway.wiseSpend.features.events.list.EventsListComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    component: EventsListComponent,
) {
    val model by component.model.collectAsState()
    val dialogSlot by component.slot.subscribeAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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

        dialogSlot.child?.instance?.let { createEventComponent ->
            ModalBottomSheet(
                onDismissRequest = component::onDismiss,
                sheetState = sheetState,
                containerColor = AppThemeProvider.colors.surface,
            ) {
                CreateEventView(createEventComponent)
            }
        }

        FAB(modifier = Modifier.align(Alignment.BottomEnd)) { component.newEvent(model.selectedDay) }
    }
}