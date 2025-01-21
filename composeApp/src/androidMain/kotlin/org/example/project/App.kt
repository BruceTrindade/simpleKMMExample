package org.example.project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.example.project.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val DarkColorPalette = darkColors(
        primary = Color.White,
        surface = Color.DarkGray,
        background = Color.Black,
        onSurface = Color.White,
    )

    val LightColorPalette = lightColors(
        primary = Color.Black,
        surface = Color.LightGray,
        background = Color.White,
        onSurface = Color.Black,
    )

    MaterialTheme(colors = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette) {
        Column {
            HomeScreen()
        }
    }
}
