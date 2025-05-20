package info.javaway.wiseSpend.settings.child.auth.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.wiseSpend.common.ui.atoms.AppButton
import info.javaway.wiseSpend.common.ui.atoms.AppCard
import info.javaway.wiseSpend.common.ui.theme.AppThemeProvider
import info.javaway.wiseSpend.settings.child.auth.AuthComponent
import info.javaway.wiseSpend.settings.child.auth.child.register.compose.RegisterDialog
import info.javaway.wiseSpend.settings.child.auth.child.signIn.compose.SignInDialog
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.enter
import wisespend.shared.generated.resources.if_you_dont_have_acc
import wisespend.shared.generated.resources.register
import wisespend.shared.generated.resources.to_sync_info

@Composable
fun AuthView(
    component: AuthComponent,
) {

    val slots by component.slots.subscribeAsState()

    AppCard {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(Res.string.to_sync_info),
                fontSize = 18.sp
            )

            AppButton(
                stringResource(Res.string.enter),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) { component.onClickOnSignIn() }

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
                    .clickable { component.onClickOnRegister() }
            )
        }
    }

    when (val child = slots.child?.instance) {
        is AuthComponent.Child.Register -> RegisterDialog(child.component)
        is AuthComponent.Child.SignIn -> SignInDialog(child.component)
        null -> { }
    }
}