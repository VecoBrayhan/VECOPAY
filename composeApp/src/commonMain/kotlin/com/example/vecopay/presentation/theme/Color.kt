package com.example.vecopay.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Colores principales basados en VecoFlow
val PrimaryPurple = Color(0xFF6366F1) // Púrpura principal
val PrimaryPurpleDark = Color(0xFF4F46E5) // Púrpura oscuro
val SecondaryBlue = Color(0xFF3B82F6) // Azul secundario
val BackgroundLight = Color(0xFFF8F9FC) // Fondo claro
val BackgroundCard = Color(0xFFFFFFFF) // Fondo de cards
val TextPrimary = Color(0xFF1E293B) // Texto principal
val TextSecondary = Color(0xFF64748B) // Texto secundario
val SuccessGreen = Color(0xFF10B981) // Verde éxito
val ErrorRed = Color(0xFFEF4444) // Rojo error
val WarningOrange = Color(0xFFF59E0B) // Naranja advertencia

// Color Scheme Light
val VecopayLightColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE9FE),
    onPrimaryContainer = PrimaryPurpleDark,

    secondary = SecondaryBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDDEAFE),
    onSecondaryContainer = Color(0xFF1E40AF),

    tertiary = SuccessGreen,
    onTertiary = Color.White,

    error = ErrorRed,
    onError = Color.White,

    background = BackgroundLight,
    onBackground = TextPrimary,

    surface = BackgroundCard,
    onSurface = TextPrimary,

    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = TextSecondary,

    outline = Color(0xFFE2E8F0),
    outlineVariant = Color(0xFFF1F5F9)
)

// Color Scheme Dark
val VecopayDarkColorScheme = darkColorScheme(
    primary = PrimaryPurple,
    onPrimary = Color.White,
    primaryContainer = PrimaryPurpleDark,
    onPrimaryContainer = Color(0xFFEDE9FE),

    secondary = SecondaryBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF1E40AF),
    onSecondaryContainer = Color(0xFFDDEAFE),

    tertiary = SuccessGreen,
    onTertiary = Color.White,

    error = ErrorRed,
    onError = Color.White,

    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF1F5F9),

    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF1F5F9),

    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFF94A3B8),

    outline = Color(0xFF475569),
    outlineVariant = Color(0xFF334155)
)