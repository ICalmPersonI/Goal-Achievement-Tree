package com.calmperson.goalachievementtree.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = SeaGreen,
    onPrimary = Color.White,
    primaryVariant = Viridian,
    secondary = Teal,
    surface = DimGray,
    onSurface = Color.White,
    background = Color.Black,
    onBackground = Color.White
)

private val LightColorPalette = lightColors(
    primary = KellyGreen,
    onPrimary = Color.White,
    primaryVariant = Malachite,
    secondary = SpringGreen,
    surface = Color.LightGray,
    onSurface = Color.Black,
    background = Color.White,
    onBackground = Teal

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun GoalAchievementTreeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}