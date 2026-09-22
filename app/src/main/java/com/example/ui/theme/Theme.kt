package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val StudyBattleColorScheme = darkColorScheme(
  primary = ElectricPurple,
  onPrimary = TextPrimary,
  primaryContainer = SurfaceDarkElevated,
  onPrimaryContainer = BrightCyan,
  secondary = BrightCyan,
  onSecondary = BgDarkNavy,
  secondaryContainer = SurfaceDark,
  onSecondaryContainer = BrightCyan,
  tertiary = NeonGold,
  onTertiary = BgDarkNavy,
  background = BgDarkNavy,
  onBackground = TextPrimary,
  surface = SurfaceDark,
  onSurface = TextPrimary,
  surfaceVariant = SurfaceDarkElevated,
  onSurfaceVariant = TextSecondary,
  outline = SurfaceBorder,
  outlineVariant = SurfaceBorderGlow,
  error = IncorrectRed,
  onError = TextPrimary,
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = StudyBattleColorScheme,
    typography = Typography,
    content = content
  )
}

