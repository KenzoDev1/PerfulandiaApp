package com.example.perfulandia.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Gold,
    onPrimary = Black,
    secondary = GoldDim,
    onSecondary = Black,
    tertiary = LightGold,
    onTertiary = Black,
    background = Black,
    onBackground = White,
    surface = DarkGrey,
    onSurface = White,
    surfaceVariant = DarkGrey,
    onSurfaceVariant = Gold
)

private val LightColorScheme = lightColorScheme(
    primary = Gold,
    onPrimary = Black,
    secondary = GoldDim,
    onSecondary = Black,
    tertiary = LightGold,
    onTertiary = Black,
    background = White,
    onBackground = Black,
    surface = Color(0xFFF5F5F5),
    onSurface = Black,
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Black
)

@Composable
fun PerfulandiaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled to enforce brand colors
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