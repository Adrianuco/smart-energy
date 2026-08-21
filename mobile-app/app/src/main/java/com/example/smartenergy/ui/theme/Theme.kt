package com.example.smartenergy.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = TextOnPrimary,
    primaryContainer = GreenPrimaryContainer,
    onPrimaryContainer = GreenPrimary,

    secondary = MintSecondary,
    onSecondary = TextOnPrimary,
    secondaryContainer = MintSecondaryContainer,
    onSecondaryContainer = MintSecondaryDark,

    background = BackgroundLight,
    onBackground = TextPrimary,

    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = GreenSurface,
    onSurfaceVariant = TextSecondary,

    error = Error,
    onError = TextOnPrimary,
    errorContainer = ErrorContainer,
    onErrorContainer = Error,

    outline = OutlineLight,
    outlineVariant = DividerLight
)

private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimaryLight,
    onPrimary = TextOnPrimary,
    primaryContainer = GreenPrimary,
    onPrimaryContainer = GreenPrimaryContainer,

    secondary = MintSecondary,
    onSecondary = TextOnPrimary,
    secondaryContainer = MintSecondaryDark,
    onSecondaryContainer = MintSecondaryContainer,

    background = TextPrimary,
    onBackground = BackgroundLight,

    surface = TextPrimary,
    onSurface = BackgroundLight,
    surfaceVariant = GreenPrimaryLight,
    onSurfaceVariant = DividerLight,

    error = Error,
    onError = TextOnPrimary,
    errorContainer = ErrorContainer,
    onErrorContainer = Error,

    outline = OutlineLight,
    outlineVariant = DividerLight
)

@Composable
fun SmartEnergyTheme(
    darkTheme: Boolean = false, // Default to light for now
    content: @Composable () -> Unit
) {
    // Always use SmartEnergy brand colors — no dynamic color
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Set status bar color to match the theme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}