package com.example.rynnarriola.searchapp.ui.base

sealed class Screen(val route : String) {

    data object HomeScreen : Screen("home_screen")
    data object SearchScreen : Screen("search_screen")

}