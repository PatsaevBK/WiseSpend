package info.javaway.wiseSpend.features.settings.child.auth.child.register.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import info.javaway.wiseSpend.features.settings.child.auth.child.register.RegisterComponent
import info.javaway.wiseSpend.features.settings.child.auth.child.register.model.RegisterContract
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppButton
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppTextField
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppToast
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.confirm_password
import wisespend.shared.generated.resources.email
import wisespend.shared.generated.resources.password
import wisespend.shared.generated.resources.register

@Composable
fun RegisterDialog(
    component: RegisterComponent,
) {

    val state by component.model.collectAsState()
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        component.effects.onEach { event ->
            when (event) {
                is RegisterContract.Effect.Error -> showMessage = event.message
            }
        }.launchIn(this)
    }

    Dialog(onDismissRequest = component::dismiss) {
        Box {

            Column(
                modifier = Modifier
                    .background(
                        color = AppThemeProvider.colorsSystem.fill.secondary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppTextField(
                    value = state.email,
                    placeholder = stringResource(Res.string.email),
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = component::changeEmail,
                )

                AppTextField(
                    value = state.password,
                    placeholder = stringResource(Res.string.password),
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = component::changePassword,
                )

                AppTextField(
                    value = state.confirmPassword,
                    placeholder = stringResource(Res.string.confirm_password),
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = component::changeConfirmPassword,
                )

                AnimatedVisibility(
                    state.isPasswordValid,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    AppButton(stringResource(Res.string.register), onClick = component::register)
                }
            }

            AppToast(showMessage) { showMessage = null }
        }
    }
}