// C:/Users/Sarah Lisley/AndroidStudioProjects/MyApplication3/app/src/main/java/com/example/myapplication/ui/theme/Theme.kt
package com.example.myapplication.ui.theme

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


// Defina seus esquemas de cores claros e escuros
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
    // ... outras cores
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
    // ... outras cores
)

@Composable
fun NutriLivreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    primaryColorHex: String? = null,
    secondaryColorHex: String? = null,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme.copy(
            primary = primaryColorHex?.let { runCatching { Color(android.graphics.Color.parseColor(it)) }.getOrElse { DarkColorScheme.primary } } ?: DarkColorScheme.primary,
            secondary = secondaryColorHex?.let { runCatching { Color(android.graphics.Color.parseColor(it)) }.getOrElse { DarkColorScheme.secondary } } ?: DarkColorScheme.secondary
        )
        else -> LightColorScheme.copy(
            primary = primaryColorHex?.let { runCatching { Color(android.graphics.Color.parseColor(it)) }.getOrElse { LightColorScheme.primary } } ?: LightColorScheme.primary,
            secondary = secondaryColorHex?.let { runCatching { Color(android.graphics.Color.parseColor(it)) }.getOrElse { LightColorScheme.secondary } } ?: LightColorScheme.secondary
        )
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}