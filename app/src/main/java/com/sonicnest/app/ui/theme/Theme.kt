package com.sonicnest.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object SonicColors {
    val Background = Color(0xFF121315)
    val Panel = Color(0xFF1E2024)
    val PanelHigh = Color(0xFF2A2D32)
    val Highlight = Color(0xFF3F434A)
    val Shadow = Color(0xFF0A0B0C)
    val Accent = Color(0xFFE8A33D)
    val AccentLight = Color(0xFFF2C36B)
    val Working = Color(0xFF6FCF8E)
    val TextPrimary = Color(0xFFEDEAE4)
    val TextSecondary = Color(0xFFA8A8AE)
}

private val SonicDarkScheme = darkColorScheme(
    primary = SonicColors.Accent,
    onPrimary = Color(0xFF1C1200),
    background = SonicColors.Background,
    onBackground = SonicColors.TextPrimary,
    surface = SonicColors.Panel,
    onSurface = SonicColors.TextPrimary,
    surfaceVariant = SonicColors.PanelHigh,
    onSurfaceVariant = SonicColors.TextSecondary,
    outline = SonicColors.Highlight
)

private val SonicTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.2.sp
    ),
    bodyMedium = TextStyle(fontSize = 14.sp)
)

@Composable
fun SonicNestTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SonicDarkScheme,
        typography = SonicTypography,
        content = content
    )
}
