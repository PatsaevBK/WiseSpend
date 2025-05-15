package info.javaway.spend_sense.categories.creation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import info.javaway.spend_sense.categories.creation.CreateCategoryData
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppTextField
import info.javaway.spend_sense.common.ui.atoms.BottomModalContainer
import org.jetbrains.compose.resources.stringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.save
import spendsense.shared.generated.resources.subtitle_category_placeholder
import spendsense.shared.generated.resources.title_category_placeholder

@Composable
fun CreateCategoryView(
    isExpanded: Boolean,
    onSaveButtonClick: (CreateCategoryData) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var rColor by remember { mutableFloatStateOf(0.3f) }
    var gColor by remember { mutableFloatStateOf(0.6f) }
    var bColor by remember { mutableFloatStateOf(0.9f) }

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            focusRequester.requestFocus()
        } else {
            focusRequester.freeFocus()
            title = ""
            subtitle = ""
            rColor = 0.3f
            gColor = 0.6f
            bColor = 0.9f
        }
    }


    BottomModalContainer {
        AppTextField(
            value = title,
            placeholder = stringResource(Res.string.title_category_placeholder),
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusRequester)
        ) { title = it }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = subtitle,
            placeholder = stringResource(Res.string.subtitle_category_placeholder),
            modifier = Modifier.fillMaxWidth()
        ) { subtitle = it }

        Spacer(modifier = Modifier.height(16.dp))

        ColorBox(rColor, gColor, bColor) {
            Column {
                ColorSlider(
                    color = Color.Red,
                    sliderValue = rColor,
                    onValueChange = { rColor = it }
                )

                ColorSlider(
                    color = Color.Green,
                    sliderValue = gColor,
                    onValueChange = { gColor = it }
                )

                ColorSlider(
                    color = Color.Blue,
                    sliderValue = bColor,
                    onValueChange = { bColor = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppButton(title = stringResource(Res.string.save)) {
            onSaveButtonClick(
                CreateCategoryData(
                    title = title,
                    subtitle = subtitle,
                    colorHex = Color(red = rColor, green = gColor, blue = bColor).toArgb().toString(16)
                )
            )
        }
    }
}