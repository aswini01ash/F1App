package com.example.racerapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.racerapplication.data.model.Race
import com.example.racerapplication.ui.detail.DetailScreen
import com.example.racerapplication.ui.home.HomeScreen
import com.google.gson.Gson

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{raceJson}") {
        fun createRoute(race: Race): String {
            val json = Gson().toJson(race)
            return "detail/$json"
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun F1Navigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDetail = { race ->
                    navController.navigate(Screen.Detail.createRoute(race))
                }
            )
        }

        composable(Screen.Detail.route) { backStackEntry ->
            val raceJson = backStackEntry.arguments?.getString("raceJson")
            val race = raceJson?.let { Gson().fromJson(it, Race::class.java) }
            DetailScreen(
                race = race,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
