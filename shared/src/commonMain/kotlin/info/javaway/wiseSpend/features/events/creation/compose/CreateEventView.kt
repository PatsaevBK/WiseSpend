package info.javaway.wiseSpend.features.events.creation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.di.DatePickerFactoryQualifier
import info.javaway.wiseSpend.di.getKoinInstance
import info.javaway.wiseSpend.features.categories.list.compose.CategoriesListView
import info.javaway.wiseSpend.features.events.creation.CreateEventComponent
import info.javaway.wiseSpend.features.events.list.compose.ChooseAccountView
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppButton
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppNumberTextField
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppTextField
import info.javaway.wiseSpend.uiLibrary.ui.atoms.BottomModalContainer
import info.javaway.wiseSpend.uiLibrary.ui.atoms.TextPairButton
import info.javaway.wiseSpend.uiLibrary.ui.calendar.compose.DatePickerView
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.accounts
import wisespend.shared.generated.resources.category
import wisespend.shared.generated.resources.cost
import wisespend.shared.generated.resources.date
import wisespend.shared.generated.resources.empty_category
import wisespend.shared.generated.resources.note
import wisespend.shared.generated.resources.save
import wisespend.shared.generated.resources.title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventView(
    component: CreateEventComponent,
    modifier: Modifier = Modifier,
) {

    val model by component.model.collectAsState()
    val categorySlot by component.categorySlot.subscribeAsState()
    val accountSlot by component.accountSlot.subscribeAsState()

    var showDateDialog by remember { mutableStateOf(false) }

    BottomModalContainer(modifier = modifier) {
        AppNumberTextField(
            value = model.cost.toString(),
            placeholder = stringResource(Res.string.cost),
            hint = stringResource(Res.string.cost),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        ) { component.changeCost(it) }

        TextPairButton(
            title = stringResource(Res.string.category),
            buttonTitle = model.selectedCategory.title.ifEmpty { stringResource(Res.string.empty_category) },
            enabled = model.isCategoriesEmpty.not(),
            colorHex = model.selectedCategory.colorHex.takeIf { it.isNotEmpty() }
        ) { component.showCategory() }

        TextPairButton(
            title = stringResource(Res.string.accounts),
            buttonTitle = model.selectedAccount.name,
        ) { component.showAccount() }

        HorizontalDivider(color = AppThemeProvider.colorsSystem.separator.primary)

        TextPairButton(
            title = stringResource(Res.string.date),
            buttonTitle = model.date.toString()
        ) { showDateDialog = true }

        HorizontalDivider(color = AppThemeProvider.colorsSystem.separator.primary)

        AppTextField(
            value = model.title,
            placeholder = stringResource(Res.string.title),
            modifier = Modifier.fillMaxWidth()
        ) { component.changeTitle(it) }

        AppTextField(
            value = model.note,
            placeholder = stringResource(Res.string.note),
            modifier = Modifier.fillMaxWidth()
        ) { component.changeNote(it) }

        AppButton(title = stringResource(Res.string.save), enabled = model.isEventValid) {
            component.finish()
        }
    }

    categorySlot.child?.instance?.let { categoryListComponent ->
        BasicAlertDialog(
            onDismissRequest = { component.dismissCategory() }
        ) {
            CategoriesListView(
                component = categoryListComponent,
                modifier = Modifier.background(
                    AppThemeProvider.colorsSystem.fill.secondary,
                    RoundedCornerShape(16.dp)
                )
            ) { category ->
                component.selectCategory(category)
                component.dismissCategory()
            }
        }
    }

    accountSlot.child?.instance?.let { accountsListComponent ->
        BasicAlertDialog(
            onDismissRequest = component::dismissAccount
        ) {
            ChooseAccountView(
                selectedAccountId = model.selectedAccount.id,
                accountListComponent = accountsListComponent,
                isTotalsInclude = false,
                onClick = { accountId ->
                    accountId?.let { component.selectAccount(it) }
                    component.dismissAccount()
                },
            )
        }
    }

    if (showDateDialog) {
        Dialog(onDismissRequest = { showDateDialog = false }) {
            DatePickerView(
                viewModel = getKoinInstance(DatePickerFactoryQualifier),
            ) { day ->
                showDateDialog = false
                component.selectDate(day.date)
            }
        }
    }
}
