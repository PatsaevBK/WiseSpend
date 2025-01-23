package info.javaway.spend_sense.settings.auth.register.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppTextField
import info.javaway.spend_sense.common.ui.atoms.AppToast
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.settings.auth.register.RegisterViewModel
import info.javaway.spend_sense.settings.auth.register.model.RegisterContract
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.confirm_password
import spendsense.shared.generated.resources.email
import spendsense.shared.generated.resources.password
import spendsense.shared.generated.resources.register

@Composable
fun RegisterDialog(
    viewModel: RegisterViewModel,
    successListener: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is RegisterContract.Event.Error -> showMessage = event.message
                RegisterContract.Event.Success -> successListener()
            }
        }.launchIn(this)
    }

    Box {

        Column(
            modifier = Modifier
                .background(AppThemeProvider.colors.surface, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTextField(
                state.email,
                stringResource(Res.string.email),
                onValueChange = viewModel::changeEmail
            )

            AppTextField(
                state.password,
                stringResource(Res.string.password),
                onValueChange = viewModel::changePassword
            )

            AppTextField(
                state.confirmPassword,
                stringResource(Res.string.confirm_password),
                onValueChange = viewModel::changeConfirmPassword
            )

            AnimatedVisibility(
                state.isPasswordValid,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AppButton(stringResource(Res.string.register), onClick = viewModel::register)
            }
        }

        AppToast(showMessage) { showMessage = null }
    }
}