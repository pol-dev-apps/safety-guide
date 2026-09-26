package dev.pol.safetyguide.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import dev.pol.safetyguide.R

sealed class Screen(val route: String) {
    data object Disclaimer : Screen("disclaimer")
    data object Home : Screen("home")
    data object Category : Screen("category/{categoryId}") {
        fun createRoute(categoryId: String) = "category/$categoryId"
    }
    data object Emergency : Screen("emergency")
    data object Settings : Screen("settings")
}

sealed class BottomNavItem(
    val screen: Screen,
    @StringRes val titleRes: Int,
    val icon: ImageVector
) {
    data object Checklist : BottomNavItem(
        screen = Screen.Home,
        titleRes = R.string.nav_checklist,
        icon = Icons.Default.CheckCircle
    )
    data object Emergency : BottomNavItem(
        screen = Screen.Emergency,
        titleRes = R.string.nav_emergency,
        icon = Icons.Default.Phone
    )
    data object Settings : BottomNavItem(
        screen = Screen.Settings,
        titleRes = R.string.nav_settings,
        icon = Icons.Default.Settings
    )
}

val bottomNavItems = listOf(
    BottomNavItem.Checklist,
    BottomNavItem.Emergency,
    BottomNavItem.Settings
)
