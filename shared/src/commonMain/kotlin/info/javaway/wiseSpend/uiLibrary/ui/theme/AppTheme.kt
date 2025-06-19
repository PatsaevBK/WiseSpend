package info.javaway.wiseSpend.uiLibrary.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

data class AppPrefs(
    val isFirstDayMonday: Boolean = true
)

private val LocalAppPrefs = staticCompositionLocalOf { AppPrefs() }
private val LocalAppColors = staticCompositionLocalOf { darkPalette }
private val LocalColorSystem = staticCompositionLocalOf { LightColorSystem }
private val LocalTypography = staticCompositionLocalOf { typographySystem }

@Composable
fun Theme(
    themeIsDark: Boolean,
    appPrefs: AppPrefs,
    content: @Composable () -> Unit
) {
    val colors = if (themeIsDark) darkPalette else lightPalette
    val colorSystem = if (themeIsDark) DarkColorSystem else LightColorSystem
    val typography = typographySystem

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppPrefs provides appPrefs,
        LocalColorSystem provides colorSystem,
        LocalTypography provides typography,
        content = content
    )
}

object AppThemeProvider {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val appPrefs: AppPrefs
        @Composable
        @ReadOnlyComposable
        get() = LocalAppPrefs.current

    val colorsSystem: ColorSystem
        @Composable
        @ReadOnlyComposable
        get() = LocalColorSystem.current

    val typography: TypographySystem
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

@Immutable
data class ColorSystem(
    val fill: FillColors,
    val text: TextColors,
    val icon: IconColors,
    val separator: SeparatorColors,
)

private val DarkColorSystem = ColorSystem(
    fill = Fill.Dark,
    text = Text.Dark,
    icon = Icon.Dark,
    separator = Separator.Dark,
)

private val LightColorSystem = ColorSystem(
    fill = Fill.Light,
    text = Text.Light,
    icon = Icon.Light,
    separator = Separator.Light,
)

@Immutable
data class TypographySystem(
    val l: TypographyStyles = Typography.L,
    val m: TypographyStyles = Typography.M,
    val s: TypographyStyles = Typography.S,
    val monospaced: Monospaced = Monospaced(),
) {
    data class Monospaced(
        val m: MonospacedStyles = Typography.Monospaced.M,
        val s: MonospacedStyles = Typography.Monospaced.S,
    )
}

private val typographySystem = TypographySystem()