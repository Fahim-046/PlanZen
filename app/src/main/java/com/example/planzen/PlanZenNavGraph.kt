package com.example.planzen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.planzen.screen.splash.SplashScreen
import com.example.planzen.screen.task.TaskScreen
import com.example.planzen.screen.task.TaskScreenViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    object Task : Screen("task")
}

// Didn't understand
sealed class Splashscreen(val route: String) {

    object Splash : Splashscreen("splashIndex")
}

// Didn't understand
sealed class Taskscreen(val route: String) {

    object Home : Splashscreen("taskIndex")
}

@Composable
fun PlanZenNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
    // goBack: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        addSplashScreen(navController = navController)
        addTaskScreen(
            navController = navController
            // goBack = goBack
        )
    }
}

// Didn't understand
private fun NavGraphBuilder.addSplashScreen(
    navController: NavHostController
) {
    navigation(
        route = Screen.Splash.route,
        startDestination = Splashscreen.Splash.route
    ) {
        composable(Splashscreen.Splash.route) {
            SplashScreen(
                gotoTaskScreen = {
                    navController.navigate(Screen.Task.route) {
                        popUpTo(Splashscreen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.addTaskScreen(
    navController: NavHostController
    // goBack: () -> Unit
) {
    navigation(
        route = Screen.Task.route,
        startDestination = Taskscreen.Home.route
    ) {
        composable(Taskscreen.Home.route) {
            val viewModel: TaskScreenViewModel = hiltViewModel()

            TaskScreen(
                viewModel = viewModel
//                goBack = {
//                    goBack()
//                }
            )
        }
    }
}
