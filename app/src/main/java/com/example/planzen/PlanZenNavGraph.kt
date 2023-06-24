package com.example.planzen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    object Task : Screen("task")
}
