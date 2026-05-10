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
        
        // 5. The Main App Hub (Dashboard + Bottom Nav)
        composable("main") {
            MainScreen(
                onEventClick = { event -> 
                    navController.navigate("eventDetails") 
                },
                onLogout = {
                    // Navigate back to the onboarding screen on logout
                    navController.navigate("home") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
        
        // 6. Alert Details (Pushed on top)
        composable("eventDetails") {
            EventDetailsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}