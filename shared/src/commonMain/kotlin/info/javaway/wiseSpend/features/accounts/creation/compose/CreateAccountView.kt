package info.javaway.wiseSpend.features.accounts.creation.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import info.javaway.wiseSpend.features.accounts.creation.CreateAccountComponent
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppButton
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppNumberTextField
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppTextField
import info.javaway.wiseSpend.uiLibrary.ui.atoms.BottomModalContainer
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.account_name
import wisespend.shared.generated.resources.amount
import wisespend.shared.generated.resources.save

@Composable
fun CreateAccountView(
    component: CreateAccountComponent,
    modifier: Modifier = Modifier,
) {

    val model by component.model.collectAsState()

    BottomModalContainer(modifier = modifier) {
        AppNumberTextField(
            value = model.account.amount.toString(),
            placeholder = stringResource(Res.string.amount),
            hint = stringResource(Res.string.amount),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        ) { component.changeAmount(it) }

        AppTextField(
            value = model.account.name,
            placeholder = stringResource(Res.string.account_name),
            modifier = Modifier.fillMaxWidth()
        ) { component.changeName(it) }

        AppButton(stringResource(Res.string.save)) {
            component.finish()
        }
    }
}