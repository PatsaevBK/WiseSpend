package info.javaway.wiseSpend.uiLibrary.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

sealed interface TypographyStyles {
    val heading1: TextStyle
        @Composable
        get() = TextStyle()
    val heading2: TextStyle
        @Composable
        get() = TextStyle()
    val heading3: TextStyle
        @Composable
        get() = TextStyle()
    val body: TextStyle
        @Composable
        get() = TextStyle()
    val label1: TextStyle
        @Composable
        get() = TextStyle()
    val label2: TextStyle
        @Composable
        get() = TextStyle()
}

sealed interface MonospacedStyles {
    val number1: TextStyle
    val number2: TextStyle
}

object Typography {
    data object L : TypographyStyles {
        override val heading1: TextStyle
            @Composable
            get() = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Normal)
        override val heading2: TextStyle
            @Composable
            get() = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Medium)
        override val heading3: TextStyle
            @Composable
            get() = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
        override val body: TextStyle
            @Composable
            get() = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
        override val label1: TextStyle
            @Composable
            get() = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal)
        override val label2: TextStyle
            @Composable
            get() = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium)
    }

    data object M : TypographyStyles {
        override val heading1: TextStyle
            @Composable
            get() = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Normal)
        override val heading2: TextStyle
            @Composable
            get() = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium)
        override val heading3: TextStyle
            @Composable
            get() = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
        override val body: TextStyle
            @Composable
            get() = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
        override val label1: TextStyle
            @Composable
            get() = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
        override val label2: TextStyle
            @Composable
            get() = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
    }

    data object S : TypographyStyles {
        override val heading1: TextStyle
            @Composable
            get() = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Normal)
        override val heading2: TextStyle
            @Composable
            get() = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium)
        override val heading3: TextStyle
            @Composable
            get() = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium)
        override val body: TextStyle
            @Composable
            get() = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal)
        override val label1: TextStyle
            @Composable
            get() = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal)
        override val label2: TextStyle
            @Composable
            get() = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium)
    }

    object Monospaced {
        data object M : MonospacedStyles {
            override val number1: TextStyle = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
            override val number2: TextStyle = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        data object S : MonospacedStyles {
            override val number1: TextStyle = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
            override val number2: TextStyle = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}