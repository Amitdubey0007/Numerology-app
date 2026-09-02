package com.numerology.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = Indigo60,
    onPrimary = White,
    primaryContainer = Indigo10,
    onPrimaryContainer = Indigo90,
    secondary = Gold60,
    onSecondary = White,
    secondaryContainer = Gold40,
    onSecondaryContainer = Slate90,
    background = BackgroundLight,
    onBackground = Slate90,
    surface = SurfaceLight,
    onSurface = Slate90,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = Slate70,
    error = ErrorRed,
    onError = White
)

private val DarkColors = darkColorScheme(
    primary = Indigo40,
    onPrimary = Slate90,
    primaryContainer = Indigo80,
    onPrimaryContainer = Indigo10,
    secondary = Gold40,
    onSecondary = Slate90,
    background = Slate90,
    onBackground = Slate10,
    surface = Indigo90,
    onSurface = Slate10,
    surfaceVariant = Indigo80,
    onSurfaceVariant = Indigo20,
    error = ErrorRed,
    onError = White
)

@Composable
fun NumerologyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // kept off by default to preserve the deliberate brand palette
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
