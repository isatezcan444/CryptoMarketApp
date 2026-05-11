package com.example.cryptomarketapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PastelGreen,
    secondary = OrchidPurple,
    tertiary = AmberYellow,

    background = White,
    surface = OffWhite,

    onPrimary = DeepBlack,
    onSecondary = White,

    error = CoralRed,
    onError = White,

    outline = SilverGray,
    onSurfaceVariant = CharcoalGray
)

private val DarkColorScheme = darkColorScheme(
    primary = VividGreen,
    secondary = OrchidPurple,
    tertiary = AmberYellow,

    background = DeepBlack,
    surface = MossGreen,

    onPrimary = White,
    onSecondary = DeepBlack,
    error = CoralRed,
    outline = CharcoalGray,
    onSurface = OffWhite
)

@Composable
fun CryptoMarketAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}