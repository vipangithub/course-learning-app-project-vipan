package com.example.courselearningapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.courselearningapp.data.AppContainer
import com.example.courselearningapp.presentation.dashboard.CourseDashboardScreen
import com.example.courselearningapp.presentation.dashboard.CourseDashboardViewModel
import com.example.courselearningapp.presentation.details.CourseDetailsScreen
import com.example.courselearningapp.presentation.details.CourseDetailsViewModel
import com.example.courselearningapp.presentation.login.LoginScreen
import com.example.courselearningapp.presentation.login.LoginViewModel

private const val LOGIN_ROUTE = "login"
private const val DASHBOARD_ROUTE = "dashboard"
private const val DETAILS_ROUTE = "details/{courseId}"
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LOGIN_ROUTE
    ) {

        composable(LOGIN_ROUTE) {

            val viewModel = remember {
                LoginViewModel()
            }

            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(DASHBOARD_ROUTE) {
                        popUpTo(LOGIN_ROUTE) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(DASHBOARD_ROUTE) {

            val viewModel = remember {
                CourseDashboardViewModel(
                    AppContainer.courseRepository
                )
            }

            CourseDashboardScreen(
                viewModel = viewModel,
                onCourseClick = { courseId ->
                    navController.navigate("details/$courseId")
                }
            )
        }
        composable(
            route = DETAILS_ROUTE,
            arguments = listOf(
                navArgument("courseId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val courseId =
                backStackEntry.arguments?.getInt("courseId")
                    ?: return@composable

            val viewModel = remember(courseId) {
                CourseDetailsViewModel(
                    repository = AppContainer.courseRepository,
                    courseId = courseId
                )
            }

            CourseDetailsScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}