package com.codealpha.collegealert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codealpha.collegealert.ui.screens.*
import com.codealpha.collegealert.ui.theme.CollegeAlertTheme
import com.codealpha.collegealert.viewmodel.AuthViewModel
import com.codealpha.collegealert.viewmodel.EventViewModel

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
    // Create shared ViewModels
    val authViewModel: AuthViewModel = viewModel()
    val eventViewModel: EventViewModel = viewModel()
    
    val userProfile = authViewModel.userProfile.value

    NavHost(navController = navController, startDestination = "splash") {
        // 1. Splash Screen
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
                        popUpTo("home") { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate("signup")
                },
                viewModel = authViewModel
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
                },
                viewModel = authViewModel
            )
        }

        // 5. The Main App Hub
        composable("main") {
            MainScreen(
                onEventClick = { event ->
                    eventViewModel.selectEvent(event)
                    navController.navigate("eventDetails")
                },
                onLogout = {
                    authViewModel.logout {
                        navController.navigate("splash") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                onAddEventClick = {
                    navController.navigate("addEvent")
                },
                onAdminDashboardClick = {
                    navController.navigate("adminDashboard")
                },
                authViewModel = authViewModel
            )
        }

        // 6. Alert Details
        composable("eventDetails") {
            val event = eventViewModel.selectedEvent.value
            if (event != null) {
                EventDetailsScreen(
                    event = event,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        // 7. Add Event Screen (Admin Mode)
        composable("addEvent") {
            AddEventScreen(
                onBackClick = { navController.popBackStack() },
                eventViewModel = eventViewModel,
                authViewModel = authViewModel
            )
        }
        
        // 8. Admin Dashboard
        composable("adminDashboard") {
            AdminDashboardScreen(
                onBackClick = { navController.popBackStack() },
                onAddNewEvent = { navController.navigate("addEvent") }
            )
        }
    }
}
