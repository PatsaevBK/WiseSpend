package info.javaway.wiseSpend.uiLibrary.ui.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val accent: Color,
    val surface: Color,
    val onSurface: Color,
    val background: Color,
    val onBackground: Color,
)

val lightPalette = AppColors(
    accent = Color(0xFFa3592f),
    background = Color(0xFFfcf5e7),
    onBackground = Color(0xFF351904),
    surface = Color(0xFFfade9d),
    onSurface = Color(0xFF5d4b37),
)

val darkPalette = AppColors(
    accent = Color(0xFFAF9363),
    background = Color(0xFF060D16),
    onBackground = Color(0xFFF6F6F6),
    surface = Color(0xFF0D1E31),
    onSurface = Color(0xFF99A6B5),
)

sealed interface FillColors {
    val primary: Color
    val secondary: Color
    val transparentLight: Color
    val transparentDark: Color
    val modal: Color
    val default: Color
    val row: Color
    val active: Color
    val components: Color
    val error: Color
    val success: Color
    val avatar: AvatarColors
    val disable: Color
    val card: CardColors
    val label: LabelColors
}

object Fill {
    data object Light : FillColors {
        override val primary: Color = Color(0xFFFFFFFF)
        override val secondary: Color = Color(0xFFF8F9FB)
        override val transparentLight: Color = Color(0xCCFFFFFF)
        override val transparentDark: Color = Color(0x33000000)
        override val modal: Color = Color(0xFFFFFFFF)
        override val default: Color = Color(0xFFF1F3F7)
        override val row: Color = Color(0xFFFFFFFF)
        override val active: Color = Color(0xFF007AFF)
        override val components: Color = Color(0xFFE3EFFC)
        override val error: Color = Color(0xFFEC2227)
        override val success: Color = Color(0xFF65BE95)
        override val avatar: AvatarColors = Avatar.Light
        override val disable: Color = Color(0xFFA9B9D2)
        override val card: CardColors = Card.Light
        override val label: LabelColors = Label.Light
    }

    data object Dark : FillColors {
        override val primary: Color = Color(0xFF000000)
        override val secondary: Color = Color(0xFF252A30)
        override val transparentLight: Color = Color(0xCC252A30)
        override val transparentDark: Color = Color(0x66000000)
        override val modal: Color = Color(0xFF252A30)
        override val default: Color = Color(0xFF1C232D)
        override val row: Color = Color(0xFF32373E)
        override val active: Color = Color(0xFF1F85EE)
        override val components: Color = Color(0xFF1A4E86)
        override val error: Color = Color(0xFFF53E44)
        override val success: Color = Color(0xFF50AF84)
        override val avatar: AvatarColors = Avatar.Dark
        override val disable: Color = Color(0xFF5A6F90)
        override val card: CardColors = Card.Dark
        override val label: LabelColors = Label.Dark
    }
}

sealed interface AvatarColors {
    val violet: Color
    val green: Color
    val red: Color
    val yellow: Color
}

object Avatar {
    data object Light : AvatarColors {
        override val violet: Color = Color(0xFF6757C8)
        override val green: Color = Color(0xFF009F96)
        override val red: Color = Color(0xFFFB5D3F)
        override val yellow: Color = Color(0xFFFFB122)
    }

    data object Dark : AvatarColors {
        override val violet: Color = Color(0xFF5444BB)
        override val green: Color = Color(0xFF007A73)
        override val red: Color = Color(0xFFE14B32)
        override val yellow: Color = Color(0xFFE99D12)
    }
}

sealed interface CardColors {
    val grey: Color
    val green: Color
    val yellow: Color
    val disable: Color
}

object Card {
    data object Light : CardColors {
        override val grey: Color = Color(0xFFE7EDF1)
        override val green: Color = Color(0xFFE0F0E3)
        override val yellow: Color = Color(0xFFF6EBDB)
        override val disable: Color = Color(0xFFF1F3F7)
    }
    data object Dark : CardColors {
        override val grey: Color = Color(0xFF2E4152)
        override val green: Color = Color(0xFF344537)
        override val yellow: Color = Color(0xFF473F31)
        override val disable: Color = Color(0xFF1C232D)
    }
}

sealed interface LabelColors {
    val grey: Color
    val green: Color
    val yellow: Color
    val disable: Color
}

object Label {
    data object Light : LabelColors {
        override val grey: Color = Color(0xFFCCD7E0)
        override val green: Color = Color(0xFFC3DEC8)
        override val yellow: Color = Color(0xFFE3D4BF)
        override val disable: Color = Color(0xFFE5E9F0)
    }
    data object Dark : LabelColors {
        override val grey: Color = Color(0xFF435666)
        override val green: Color = Color(0xFF3A5C40)
        override val yellow: Color = Color(0xFF615139)
        override val disable: Color = Color(0xFF27313F)
    }
}

sealed interface IconColors {
    val primary: Color
    val secondary: Color
    val tertiary: Color
    val controls: Color
    val active: Color
    val selection: Color
}

object Icon {
    data object Light : IconColors {
        override val primary: Color = Color(0xFF252A30)
        override val secondary: Color = Color(0xFF708BB4)
        override val tertiary: Color = Color(0xFFB7C5D9)
        override val controls: Color = Color(0xFFFFFFFF)
        override val active: Color = Color(0xFF007AFF)
        override val selection: Color = Color(0x4D007AFF)
    }

    data object Dark : IconColors {
        override val primary: Color = Color(0xFFFFFFFF)
        override val secondary: Color = Color(0xFF94AED6)
        override val tertiary: Color = Color(0xFF708BB4)
        override val controls: Color = Color(0xFFFFFFFF)
        override val active: Color = Color(0xFF1F85EE)
        override val selection: Color = Color(0x4D007AFF)
    }
}

sealed interface SeparatorColors {
    val primary: Color
    val secondary: Color
}

object Separator {
    data object Light : SeparatorColors {
        override val primary: Color = Color(0xFFF1F3F7)
        override val secondary: Color = Color(0xFFDEDFE3)
    }

    data object Dark : SeparatorColors {
        override val primary: Color = Color(0xFF1C232D)
        override val secondary: Color = Color(0xFF2D394A)
    }
}

sealed interface TextColors {
    val primary: Color
    val secondary: Color
    val tertiary: Color
    val controls: Color
    val caption: Color
    val link: Color
}

object Text {
    data object Light : TextColors {
        override val primary: Color = Color(0xFF252A30)
        override val secondary: Color = Color(0xFF708BB4)
        override val tertiary: Color = Color(0xFFB7C5D9)
        override val controls: Color = Color(0xFFFFFFFF)
        override val caption: Color = Color(0xFFA9B9D2)
        override val link: Color = Color(0xFF0070EA)
    }

    data object Dark : TextColors {
        override val primary: Color = Color(0xFFFFFFFF)
        override val secondary: Color = Color(0xFF708BB4)
        override val tertiary: Color = Color(0xFF94AED6)
        override val controls: Color = Color(0xFFFFFFFF)
        override val caption: Color = Color(0xFF5A6F90)
        override val link: Color = Color(0xFF2C91FF)
    }
}