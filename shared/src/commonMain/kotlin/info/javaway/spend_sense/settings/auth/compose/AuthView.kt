package info.javaway.spend_sense.settings.auth.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppCard
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.settings.auth.register.compose.RegisterDialog
import info.javaway.spend_sense.settings.auth.signIn.compose.SignInDialog
import org.jetbrains.compose.resources.stringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.enter
import spendsense.shared.generated.resources.if_you_dont_have_acc
import spendsense.shared.generated.resources.register
import spendsense.shared.generated.resources.to_sync_info

@Composable
fun AuthView(
    successListener: () -> Unit
) {
    var showSignInDialog by remember { mutableStateOf(false) }
    var showRegisterDialog by remember { mutableStateOf(false) }

    AppCard {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(Res.string.to_sync_info),
                fontSize = 18.sp
            )

            AppButton(
                stringResource(Res.string.enter),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) { showSignInDialog = true }

            Text(
                text = buildAnnotatedString {
                    append(stringResource(Res.string.if_you_dont_have_acc))
                    withStyle(
                        style = SpanStyle(
                            color = AppThemeProvider.colors.accent,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold
                        )
                    ) { append(stringResource(Res.string.register)) }
                },
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                    .clickable { showRegisterDialog = true }
            )
        }
    }

    if (showSignInDialog) {
        Dialog(onDismissRequest = { showSignInDialog = false }) {
            SignInDialog(getKoinInstance()) {
                showSignInDialog = false
                successListener()
            }
        }
    }

    if (showRegisterDialog) {
        Dialog(onDismissRequest = { showRegisterDialog = false }) {
            RegisterDialog(getKoinInstance()) {
                showRegisterDialog = false
                successListener()
            }
        }
    }
}