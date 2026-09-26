package dev.pol.safetyguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.pol.safetyguide.data.repository.PreferencesRepository
import dev.pol.safetyguide.ui.navigation.NavGraph
import dev.pol.safetyguide.ui.navigation.Screen
import dev.pol.safetyguide.ui.navigation.bottomNavItems
import dev.pol.safetyguide.ui.theme.PrepperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val disclaimerAccepted = PreferencesRepository.isDisclaimerAccepted(applicationContext)

        if (!disclaimerAccepted) {
            setTheme(R.style.Theme_Prepper_Splash)
            installSplashScreen()
        }

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            PrepperTheme {
                MainScreen(startDestination = if (disclaimerAccepted) Screen.Home.route else Screen.Disclaimer.route)
            }
        }
    }
}

@Composable
fun MainScreen(startDestination: String = Screen.Disclaimer.route) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Emergency.route,
        Screen.Settings.route
    ) || currentRoute?.startsWith("category/") == true

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = stringResource(item.titleRes)
                                )
                            },
                            label = { Text(stringResource(item.titleRes)) },
                            selected = currentRoute == item.screen.route ||
                                    (item.screen == Screen.Home && currentRoute?.startsWith("category/") == true),
                            onClick = {
                                if (currentRoute != item.screen.route) {
                                    val didPop = navController.popBackStack(
                                        item.screen.route, inclusive = false
                                    )
                                    if (!didPop) {
                                        navController.navigate(item.screen.route) {
                                            popUpTo(Screen.Home.route) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            onDisclaimerAccepted = {},
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
