package com.numerology.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.numerology.app.ui.screens.CompatibilityScreen
import com.numerology.app.ui.screens.DailyNumberScreen
import com.numerology.app.ui.screens.FullProfileScreen
import com.numerology.app.ui.screens.HomeScreen
import com.numerology.app.ui.screens.LifePathScreen
import com.numerology.app.ui.screens.NameNumberScreen

object Routes {
    const val HOME = "home"
    const val LIFE_PATH = "life_path"
    const val NAME_NUMBER = "name_number"
    const val DAILY_NUMBER = "daily_number"
    const val COMPATIBILITY = "compatibility"
    const val FULL_PROFILE = "full_profile"
}

/**
 * Root navigation graph. Holds the saved Life Path Number in memory so
 * it can be reused by the Home screen's "Today's Number" card and the
 * Daily Number screen without recalculating or requiring a database.
 *
 * NOTE: this is in-memory only (resets on process death). Wiring it to
 * DataStore for persistence across app restarts is a natural next step
 * once the UI is validated.
 */
@Composable
fun NumerologyNavGraph(navController: NavHostController = rememberNavController()) {
    var savedLifePathNumber by remember { mutableStateOf<Int?>(null) }

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                savedLifePathNumber = savedLifePathNumber,
                onNavigateLifePath = { navController.navigate(Routes.LIFE_PATH) },
                onNavigateName = { navController.navigate(Routes.NAME_NUMBER) },
                onNavigateDaily = { navController.navigate(Routes.DAILY_NUMBER) },
                onNavigateCompatibility = { navController.navigate(Routes.COMPATIBILITY) },
                onNavigateFullProfile = { navController.navigate(Routes.FULL_PROFILE) }
            )
        }
        composable(Routes.LIFE_PATH) {
            LifePathScreen(
                onBack = { navController.popBackStack() },
                onResultCalculated = { number -> savedLifePathNumber = number }
            )
        }
        composable(Routes.NAME_NUMBER) {
            NameNumberScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.DAILY_NUMBER) {
            DailyNumberScreen(
                savedLifePathNumber = savedLifePathNumber,
                onBack = { navController.popBackStack() },
                onGoToLifePath = {
                    navController.navigate(Routes.LIFE_PATH) {
                        popUpTo(Routes.DAILY_NUMBER) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.COMPATIBILITY) {
            CompatibilityScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.FULL_PROFILE) {
            FullProfileScreen(onBack = { navController.popBackStack() })
        }
    }
}
