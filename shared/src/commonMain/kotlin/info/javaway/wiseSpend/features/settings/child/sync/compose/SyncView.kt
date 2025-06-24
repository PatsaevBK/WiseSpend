package info.javaway.wiseSpend.features.settings.child.sync.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.javaway.wiseSpend.features.settings.child.sync.SyncComponent
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppButton
import info.javaway.wiseSpend.uiLibrary.ui.atoms.AppCard
import info.javaway.wiseSpend.uiLibrary.ui.theme.AppThemeProvider
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.auth_info
import wisespend.shared.generated.resources.logout
import wisespend.shared.generated.resources.sync

@Composable
fun SyncView(
    component: SyncComponent,
    modifier: Modifier = Modifier,
) {

    val model by component.model.collectAsState()

    Box(modifier) {
        AppCard {
            Column {
                Text(
                    text = stringResource(Res.string.auth_info, model.email),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp
                )
                AppButton(
                    title = stringResource(Res.string.sync),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    onClick = component::sync
                )
                AppButton(
                    title = stringResource(Res.string.logout),
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    onClick = component::logout
                )
            }
        }

        if (model.isLoading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(AppThemeProvider.colorsSystem.fill.transparentDark)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = AppThemeProvider.colorsSystem.icon.tertiary
                )
            }
        }
    }
}