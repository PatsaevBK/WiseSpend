package info.javaway.wiseSpend.settings.child.auth.child.signIn.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import info.javaway.wiseSpend.common.ui.atoms.AppButton
import info.javaway.wiseSpend.common.ui.atoms.AppTextField
import info.javaway.wiseSpend.common.ui.atoms.AppToast
import info.javaway.wiseSpend.common.ui.theme.AppThemeProvider
import info.javaway.wiseSpend.settings.child.auth.child.signIn.SignInComponent
import info.javaway.wiseSpend.settings.child.auth.child.signIn.model.SignInContract
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.email
import wisespend.shared.generated.resources.login
import wisespend.shared.generated.resources.password

@Composable
fun SignInDialog(
    signInComponent: SignInComponent
) {

    val model by signInComponent.model.collectAsState()
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        signInComponent.effects.onEach { event ->
            when (event) {
                is SignInContract.Effect.Error -> showMessage = event.message
            }
        }.launchIn(this)
    }

    Dialog(onDismissRequest = signInComponent::onDismiss){
        Box {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(
                        AppThemeProvider.colors.surface,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                AppTextField(
                    model.email,
                    stringResource(Res.string.email),
                    onValueChange = signInComponent::changeEmail
                )

                AppTextField(
                    model.password,
                    stringResource(Res.string.password),
                    onValueChange = signInComponent::changePassword
                )

                AppButton(stringResource(Res.string.login), onClick = signInComponent::login)
            }

            AppToast(showMessage) { showMessage = null }
        }
    }

}