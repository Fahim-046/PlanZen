package com.example.planzen.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planzen.Screen
import com.example.planzen.screen.splash.SplashScreen
import com.example.planzen.screen.task.TaskScreen
import com.example.planzen.screen.task.TaskScreenViewModel

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Task.route) {
            val viewModel: TaskScreenViewModel = hiltViewModel()

            TaskScreen(
                viewModel = viewModel,
                navController
            )
        }
    }
}
