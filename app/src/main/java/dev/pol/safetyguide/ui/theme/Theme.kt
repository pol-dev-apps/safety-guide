package dev.pol.safetyguide.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Green80,
    secondary = Orange80,
    tertiary = Red80,
    background = DarkGray,
    surface = MediumGray,
    surfaceVariant = LightGray,
    onPrimary = DarkGray,
    onSecondary = DarkGray,
    onTertiary = DarkGray,
    onBackground = Green80,
    onSurface = Green80,
    onSurfaceVariant = GreenGrey80
)

private val LightColorScheme = lightColorScheme(
    primary = Green40,
    secondary = Orange40,
    tertiary = Red40,
    background = androidx.compose.ui.graphics.Color.White,
    surface = androidx.compose.ui.graphics.Color.White,
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFF5F5F5),
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    onBackground = DarkGray,
    onSurface = DarkGray,
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF616161)
)

@Composable
fun PrepperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
