package info.javaway.spend_sense.events.creation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import info.javaway.spend_sense.categories.list.compose.CategoriesListView
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppTextField
import info.javaway.spend_sense.common.ui.atoms.BottomModalContainer
import info.javaway.spend_sense.common.ui.atoms.TextPairButton
import info.javaway.spend_sense.common.ui.calendar.compose.CalendarColors
import info.javaway.spend_sense.common.ui.calendar.compose.DatePickerView
import info.javaway.spend_sense.common.ui.calendar.model.CalendarDay
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.di.DatePickerFactoryQualifier
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.events.creation.CreateEventContract
import info.javaway.spend_sense.events.creation.CreateEventViewModel
import info.javaway.spend_sense.events.models.SpendEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.category
import spendsense.shared.generated.resources.cost
import spendsense.shared.generated.resources.date
import spendsense.shared.generated.resources.empty_category
import spendsense.shared.generated.resources.save
import spendsense.shared.generated.resources.spend_to

@Composable
fun CreateEventView(
    isExpand: Boolean,
    selectedDay: CalendarDay?,
    viewModel: CreateEventViewModel,
    modifier: Modifier = Modifier,
    createListener: (SpendEvent) -> Unit
) {

    val state by viewModel.state.collectAsState()
    var showDateDialog by remember { mutableStateOf(false) }
    var showCategoriesDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isExpand) {
        if (isExpand) {
            viewModel.selectDate(selectedDay?.date)
        } else {
            viewModel.resetState()
        }

        viewModel.events.onEach { event ->
            when (event) {
                is CreateEventContract.Event.Finish -> createListener(event.spendEvent)
            }
        }.launchIn(this)
    }


    BottomModalContainer(modifier = modifier) {
        TextPairButton(
            title = stringResource(Res.string.category),
            buttonTitle = state.category.title.ifEmpty { stringResource(Res.string.empty_category) },
            colorHex = state.category.colorHex.takeIf { it.isNotEmpty() }
        ) { showCategoriesDialog = true }

        TextPairButton(
            title = stringResource(Res.string.date),
            buttonTitle = state.date.toString()
        ) { showDateDialog = true }

        AppTextField(
            value = state.title,
            placeholder = stringResource(Res.string.spend_to),
            modifier = Modifier.fillMaxWidth()
        ) { viewModel.changeTitle(it) }

        AppTextField(
            value = state.note,
            placeholder = "note",
            modifier = Modifier.fillMaxWidth()
        ) { viewModel.changeNote(it) }

        AppTextField(
            value = state.cost.toString(),
            placeholder = stringResource(Res.string.cost),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        ) { viewModel.changeCost(it) }

        AppButton(stringResource(Res.string.save)) {
            viewModel.finish()
        }
    }


    if (showCategoriesDialog) {
        Dialog(
            onDismissRequest = { showCategoriesDialog = false }
        ) {
            CategoriesListView(
                component = getKoinInstance(),
                modifier = Modifier.background(
                    AppThemeProvider.colors.surface,
                    RoundedCornerShape(16.dp)
                )
            ) { category ->
                showCategoriesDialog = false
                viewModel.selectCategory(category)
            }
        }
    }

    if (showDateDialog) {
        Dialog(onDismissRequest = { showDateDialog = false }) {
            DatePickerView(
                viewModel = getKoinInstance(DatePickerFactoryQualifier),
                colors = CalendarColors.default.copy(
                    colorAccent = AppThemeProvider.colors.accent,
                    colorOnSurface = AppThemeProvider.colors.onSurface,
                    colorSurface = AppThemeProvider.colors.surface
                )
            ){ day ->
                showDateDialog = false
                viewModel.selectDate(day.date)
            }
        }
    }

}
