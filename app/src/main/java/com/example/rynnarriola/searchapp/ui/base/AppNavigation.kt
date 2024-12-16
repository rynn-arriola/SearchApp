package com.example.rynnarriola.searchapp.ui.base

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rynnarriola.searchapp.ui.screen.HomeScreen
import com.example.rynnarriola.searchapp.ui.screen.SearchScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable( route = Screen.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen()
        }
    }
}