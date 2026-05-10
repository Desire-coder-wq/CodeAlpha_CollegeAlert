package com.codealpha.collegealert.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1A237E),
    secondary = Color(0xFFFF9800),
    background = Color(0xFF0A1428),
    surface = Color(0xFF1E2937),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    surfaceVariant = Color.White // Used for our cards to ensure they stay white
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1A237E),
    secondary = Color(0xFFFF9800),
    background = Color(0xFFF8F9FB),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1A237E),
    onSurface = Color(0xFF1A237E)
)

@Composable
fun CollegeAlertTheme(
    content: @Composable () -> Unit
) {
    // Forcing a premium feel that works for both modes
    MaterialTheme(
        colorScheme = LightColorScheme, // Using Light as base for the white-card design
        content = content
    )
}