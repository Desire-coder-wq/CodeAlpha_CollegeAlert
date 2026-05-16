package com.codealpha.collegealert

import android.os.Bundle
import android.widget.Toast
import android.os.Handler
import android.os.Looper
import java.io.PrintWriter
import java.io.StringWriter
import java.io.File
import java.util.Date
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
import com.codealpha.collegealert.util.Logger
import android.util.Log
import com.codealpha.collegealert.ui.theme.CollegeAlertTheme
import com.codealpha.collegealert.viewmodel.AuthViewModel
import com.codealpha.collegealert.viewmodel.EventViewModel
import com.google.firebase.messaging.FirebaseMessaging
// Rollbar dependency removed to avoid build issues on machines without the artifact

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Install a default uncaught exception handler to capture crashes to a local file
        val priorHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val sw = StringWriter()
                val pw = PrintWriter(sw)
                throwable.printStackTrace(pw)
                val trace = sw.toString()
                try {
                    val f = File(filesDir, "crash_log.txt")
                    f.appendText("--- Crash at: ${Date()} ---\n")
                    f.appendText(trace)
                    f.appendText("\n\n")
                } catch (io: Exception) {
                    // ignore file write failures
                }
                // notify user on main thread
                Handler(Looper.getMainLooper()).post {
                    try {
                        Toast.makeText(this, "App crashed: ${throwable.message}", Toast.LENGTH_LONG).show()
                    } catch (_: Exception) {
                    }
                }
            } catch (_: Exception) {
            }
            // delegate to previous handler
            priorHandler?.uncaughtException(thread, throwable)
        }

        // (Optional) Initialize external error tracking here if available. Skipped in this build.

        setContent {
            CollegeAlertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation()
                }
            }
        }

        // Request FCM token to enable push notifications and log it for debugging
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    try { Logger.log(this, "FCM", "Token: $token") } catch (_: Exception) {}
                } else {
                    try { Logger.log(this, "FCM", "Token retrieval failed: ${task.exception?.message}") } catch (_: Exception) {}
                }
            }
        } catch (e: Exception) {
            // ignore if Firebase not configured
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
                    try {
                        // use the Compose LocalContext captured above rather than `this` (which inside a NavHost lambda is not an Android Context)
                        Logger.log(context, "Navigation", "Navigating to main after login")
                        navController.navigate("main") {
                            popUpTo("home") { inclusive = true }
                        }
                    } catch (t: Throwable) {
                        Logger.logException(context, "NavigationError", t)
                        // show fallback toast and avoid crash
                        try { Toast.makeText(context, "Navigation failed: ${t.message}", Toast.LENGTH_LONG).show() } catch (_: Exception) {}
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
                    try {
                        Logger.log(context, "Navigation", "Navigating to main after signup")
                        navController.navigate("main") {
                            popUpTo("home") { inclusive = true }
                        }
                    } catch (t: Throwable) {
                        Logger.logException(context, "NavigationError", t)
                        try { Toast.makeText(context, "Navigation failed: ${t.message}", Toast.LENGTH_LONG).show() } catch (_: Exception) {}
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
                        try {
                            Logger.log(context, "Navigation", "Logging out and navigating to splash")
                            navController.navigate("splash") {
                                // use startDestinationId instead of numeric 0 which can crash
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        } catch (t: Throwable) {
                            Logger.logException(context, "NavigationError", t)
                            try { Toast.makeText(context, "Logout navigation failed: ${t.message}", Toast.LENGTH_LONG).show() } catch (_: Exception) {}
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
                onAddNewEvent = { navController.navigate("addEvent") },
                authViewModel = authViewModel
            )
        }
    }
}
