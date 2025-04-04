package com.blumenstreetdoo.nora_pub.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import com.blumenstreetdoo.nora_pub.R

val NoraColors = lightColorScheme(
    primary = Color(0xFFFAAB1A), // yellow_accent
    secondary = Color(0xFFF6DFA7), // yellow
    background = Color(0xFFF5F5F5), // white_variant
    error = Color(0xFFF56B6C), // red
    onPrimary = Color(0xFF232F34), // grey_dark
    onSecondary = Color(0xFF212121), // black
    onBackground = Color(0xFF212121), // black
    surface = Color(0xFFF5F5F5), // white_variant
    onSurface = Color(0xFF212121), // black
)

val NoraFontFamily = FontFamily(
    Font(R.font.open_sans_regular, FontWeight.Normal),
    Font(R.font.open_sans_bold, FontWeight.Bold)
)

val NoraTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = NoraFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = NoraFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = NoraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = NoraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = NoraFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = NoraFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)
