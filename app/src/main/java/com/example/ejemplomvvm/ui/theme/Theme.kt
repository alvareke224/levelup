package com.example.ejemplomvvm.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    secondary = NeonGreen,
    background = Black,
    surface = Black,
    onPrimary = White,
    onSecondary = Black,
    onBackground = White,
    onSurface = White
)

@Composable
fun EjemploMvvmTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
