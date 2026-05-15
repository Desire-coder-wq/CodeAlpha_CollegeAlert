package com.codealpha.collegealert

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codealpha.collegealert.ui.screens.*
import com.codealpha.collegealert.ui.theme.CollegeAlertTheme
import com.codealpha.collegealert.viewmodel.AuthViewModel
import com.codealpha.collegealert.viewmodel.EventViewModel
import com.rollbar.android.Rollbar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Rollbar for professional error tracking
        Rollbar.init(this)
        
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
    val authViewModel: AuthViewModel = viewModel()
    val eventViewModel: EventViewModel = viewModel()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
        }

        composable("home") {
            HomeScreen(
                onGetStartedClick = { navController.navigate("login") }
            )
        }

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

        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = {
                    // SPEED OPTIMIZATION: Immediate welcome and redirect
                    Toast.makeText(context, "Welcome to Challenge Alert!", Toast.LENGTH_LONG).show()
                    navController.navigate("main") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.popBackStack()
                },
                viewModel = authViewModel
            )
        }

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

        composable("eventDetails") {
            val event = eventViewModel.selectedEvent.value
            if (event != null) {
                EventDetailsScreen(
                    event = event,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable("addEvent") {
            AddEventScreen(
                onBackClick = { navController.popBackStack() },
                eventViewModel = eventViewModel,
                authViewModel = authViewModel
            )
        }
        
        composable("adminDashboard") {
            AdminDashboardScreen(
                onBackClick = { navController.popBackStack() },
                onAddNewEvent = { navController.navigate("addEvent") }
            )
        }
    }
}
