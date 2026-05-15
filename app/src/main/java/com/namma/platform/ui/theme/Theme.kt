package com.namma.platform.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = NammaBlue,
    secondary = NammaYellow,
    tertiary = NammaRed,
    background = NammaLightGray,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun NammaPlatformTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme // Force light theme for high contrast consistency based on design

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
