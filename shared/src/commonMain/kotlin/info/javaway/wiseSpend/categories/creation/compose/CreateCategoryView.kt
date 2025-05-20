package info.javaway.wiseSpend.categories.creation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import info.javaway.wiseSpend.categories.creation.CreateCategoryComponent
import info.javaway.wiseSpend.common.ui.atoms.AppButton
import info.javaway.wiseSpend.common.ui.atoms.AppTextField
import info.javaway.wiseSpend.common.ui.atoms.BottomModalContainer
import org.jetbrains.compose.resources.stringResource
import wisespend.shared.generated.resources.Res
import wisespend.shared.generated.resources.save
import wisespend.shared.generated.resources.subtitle_category_placeholder
import wisespend.shared.generated.resources.title_category_placeholder

@Composable
fun CreateCategoryView(
    component: CreateCategoryComponent,
    modifier: Modifier = Modifier,
) {
    val model by component.model.collectAsState()

    val focusRequester = remember { FocusRequester() }

    DisposableEffect(Unit) {
        onDispose {
            component.onDismiss()
        }
    }

    BottomModalContainer(modifier = modifier) {
        AppTextField(
            value = model.title,
            placeholder = stringResource(Res.string.title_category_placeholder),
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusRequester)
        ) { component.changeTitle(it) }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = model.subtitle,
            placeholder = stringResource(Res.string.subtitle_category_placeholder),
            modifier = Modifier.fillMaxWidth()
        ) { component.changeSubtitle(it) }

        Spacer(modifier = Modifier.height(16.dp))

        ColorBox(rColor = model.color.red, gColor = model.color.green, bColor = model.color.blue) {
            Column {
                ColorSlider(
                    color = Color.Red,
                    sliderValue = model.color.red,
                    onValueChange = { component.changeColor(model.color.copy(red = it)) }
                )

                ColorSlider(
                    color = Color.Green,
                    sliderValue = model.color.green,
                    onValueChange = { component.changeColor(model.color.copy(green = it)) }
                )

                ColorSlider(
                    color = Color.Blue,
                    sliderValue = model.color.blue,
                    onValueChange = { component.changeColor(model.color.copy(blue = it)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppButton(title = stringResource(Res.string.save)) { component.saveCategory() }
    }
}