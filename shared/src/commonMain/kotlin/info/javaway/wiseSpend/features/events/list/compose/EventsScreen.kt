package info.javaway.wiseSpend.features.events.list.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.di.DatePickerSingleQualifier
import info.javaway.wiseSpend.di.getKoinInstance
import info.javaway.wiseSpend.features.events.creation.compose.CreateEventView
import info.javaway.wiseSpend.features.events.list.EventsListComponent
import info.javaway.wiseSpend.uiLibrary.ui.atoms.FAB
import info.javaway.wiseSpend.uiLibrary.ui.atoms.RootBox
import info.javaway.wiseSpend.uiLibrary.ui.calendar.compose.DatePickerView
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    component: EventsListComponent,
    modifier: Modifier = Modifier,
) {
    val model by component.model.collectAsState()
    val createEventSlot by component.createEventSlot.subscribeAsState()
    val accountSlot by component.accountsSlot.subscribeAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val walletPainter = rememberVectorPainter(Icons.Outlined.Wallet)
    val totalWalletPainter = rememberVectorPainter(Icons.Outlined.Money)

    Scaffold(
        modifier = modifier,
        containerColor = AppThemeProvider.colorsSystem.fill.primary,
        floatingActionButton = {
            FAB { component.newEvent(model.selectedDay) }
        },
        topBar = {
            val icon = if (model.selectedAccountId == null)
                totalWalletPainter
            else walletPainter
            EventScreenTopBar(
                accountName = model.selectedAccountUi.name,
                accountAmount = model.selectedAccountUi.formattedAmount,
                icon = icon
            ) {
                component.showAccounts()
            }
        }
    ) {
        RootBox(Modifier.padding(it)) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DatePickerView(
                    viewModel = getKoinInstance(DatePickerSingleQualifier),
                    firstDayIsMonday = true,
                    labels = model.calendarLabels,
                    selectDayListener = component::selectDay
                )

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(model.eventsByDay) { spendEvent ->
                        SpendEventItem(spendEvent, Modifier.clickable { component.editEvent(spendEvent.id) })
                        HorizontalDivider()
                    }
                }
            }
        }
    }

    createEventSlot.child?.instance?.let { createEventComponent ->
        ModalBottomSheet(
            onDismissRequest = component::onEventDismiss,
            sheetState = sheetState,
            containerColor = AppThemeProvider.colorsSystem.fill.secondary,
        ) {
            CreateEventView(createEventComponent)
        }
    }

    accountSlot.child?.instance?.let {
        BasicAlertDialog(
            onDismissRequest = component::onAccountsDismiss
        ) {
            ChooseAccountView(
                accountListComponent = it,
                selectedAccountId = model.selectedAccountId,
                isTotalsInclude = true,
                onClick = { accountsListComponent ->
                    component.selectAccount(accountsListComponent)
                    component.onAccountsDismiss()
                },
            )
        }
    }
}