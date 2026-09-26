package dev.pol.safetyguide.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.pol.safetyguide.ui.screens.CategoryScreen
import dev.pol.safetyguide.ui.screens.DisclaimerScreen
import dev.pol.safetyguide.ui.screens.EmergencyScreen
import dev.pol.safetyguide.ui.screens.HomeScreen
import dev.pol.safetyguide.ui.screens.SettingsScreen
import dev.pol.safetyguide.viewmodel.SettingsViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    onDisclaimerAccepted: () -> Unit,
    startDestination: String = Screen.Disclaimer.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Disclaimer.route) {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            DisclaimerScreen(
                onAccept = {
                    settingsViewModel.acceptDisclaimer()
                    onDisclaimerAccepted()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Disclaimer.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate(Screen.Category.createRoute(categoryId))
                },
                onHouseholdClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(
            route = Screen.Category.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
            CategoryScreen(
                categoryId = categoryId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Emergency.route) {
            EmergencyScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
