package com.example.racerapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.racerapplication.data.model.Race
import com.example.racerapplication.ui.detail.DetailScreen
import com.example.racerapplication.ui.home.HomeScreen
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{raceJson}") {
        fun createRoute(race: Race): String {
            val json = Gson().toJson(race)
            val encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
            return "detail/$encodedJson"
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun F1Navigation() {
    val navController = rememberNavController()

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

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("raceJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("raceJson")
            val raceJson = encodedJson?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            }
            val race = raceJson?.let { Gson().fromJson(it, Race::class.java) }

            DetailScreen(
                race = race,
            )
        }
    }
}
