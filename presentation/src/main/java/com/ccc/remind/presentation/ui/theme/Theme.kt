package com.ccc.remind.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf


private val LightRemindThemeColors = remindLightColorScheme()
private val DarkRemindThemeColors = remindDarkColorScheme()

private val RemindThemeTypography = remindTypography()


@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val customColorScheme = when {
        isDarkTheme -> DarkRemindThemeColors
        else -> LightRemindThemeColors
    }

    MaterialTheme(
        content = {
            CompositionLocalProvider(
                RemindLocalColorScheme provides customColorScheme,
                RemindLocalTypography provides RemindThemeTypography
            ) {
                Surface(content = content)
            }
        }
    )
}


object RemindMaterialTheme {
    val colorScheme: RemindColorScheme
    @Composable
    @ReadOnlyComposable
    get() = RemindLocalColorScheme.current

    val typography: RemindTypography
    @Composable
    @ReadOnlyComposable
    get() = RemindLocalTypography.current
}

internal val RemindLocalColorScheme = staticCompositionLocalOf { remindLightColorScheme() }
internal val RemindLocalTypography = staticCompositionLocalOf { remindTypography() }