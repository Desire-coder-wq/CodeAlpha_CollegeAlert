package com.codealpha.collegealert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codealpha.collegealert.ui.screens.*
import com.codealpha.collegealert.ui.theme.CollegeAlertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollegeAlertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        // 1. Splash Screen - Entry point
        composable("splash") {
            SplashScreen(navController = navController)
        }

        // 2. Onboarding Screen
        composable("home") {
            HomeScreen(
                onGetStartedClick = { navController.navigate("login") }
            )
        }

        // 3. Login Screen
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main") {
                        // Clear history so you can't go back to Login/Home
                        popUpTo("home") { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate("signup")
                }
            )
        }

        // 4. Registration Screen
        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate("login")
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // 5. The Main App Hub (Dashboard + Tabs)
        composable("main") {
            MainScreen(
                onEventClick = { event ->
                    // Navigate to details when an event is clicked
                    navController.navigate("eventDetails")
                },
                onLogout = {
                    // CRITICAL: Clear all history and restart at Splash
                    navController.navigate("splash") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onAddEventClick = {
                    navController.navigate("addEvent")
                }
            )
        }

        // 6. Alert Details
        composable("eventDetails") {
            EventDetailsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // 7. Add Event Screen (Admin Mode)
        composable("addEvent") {
            AddEventScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
