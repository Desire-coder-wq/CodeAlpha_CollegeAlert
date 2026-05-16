package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codealpha.collegealert.data.model.Event
import com.codealpha.collegealert.viewmodel.AuthViewModel
import com.codealpha.collegealert.util.Logger
import androidx.compose.ui.platform.LocalContext

sealed class Screen(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("dashboard_home", "Home", Icons.Default.GridView)
    object Alerts : Screen("alerts", "Alerts", Icons.Default.NotificationsNone)
    object Discover : Screen("discover", "Discover", Icons.Default.Explore)
    object Profile : Screen("profile_tab", "Profile", Icons.Default.PersonOutline)
}

@Composable
fun MainScreen(
    onEventClick: (Event) -> Unit,
    onLogout: () -> Unit,
    onAddEventClick: () -> Unit,
    onAdminDashboardClick: () -> Unit,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    try { Logger.log(context, "MainScreen", "Composed MainScreen; userProfile isAdmin=${authViewModel.userProfile.value?.isAdmin}") } catch (_: Exception) {}
    val items = listOf(
        Screen.Home,
        Screen.Alerts,
        Screen.Discover,
        Screen.Profile
    )
    
    val userProfile by authViewModel.userProfile

    Scaffold(
        floatingActionButton = {
            if (userProfile?.isAdmin == true) {
                Column {
                    FloatingActionButton(
                        onClick = onAdminDashboardClick,
                        containerColor = Color(0xFF1A237E),
                        contentColor = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(Icons.Default.Dashboard, contentDescription = "Admin Panel")
                    }
                    FloatingActionButton(
                        onClick = onAddEventClick,
                        containerColor = Color(0xFFFF9800),
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Alert")
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Simplified navigation to avoid version-specific build errors
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFFF9800),
                            selectedTextColor = Color(0xFFFF9800),
                            indicatorColor = Color(0xFFFFF3E0),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                DashboardScreen(
                    onEventClick = onEventClick,
                    authViewModel = authViewModel
                )
            }
            composable(Screen.Alerts.route) {
                AlertsScreen(
                    onEventClick = onEventClick
                )
            }
            composable(Screen.Discover.route) {
                ExploreScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onLogout = onLogout,
                    viewModel = authViewModel
                )
            }
        }
        // Debug overlay: shows whether profile has loaded and admin status
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.BottomEnd) {
            androidx.compose.material3.Surface(
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.wrapContentSize().padding(8.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            ) {
                val statusText = "admin=${userProfile?.isAdmin} loaded=${userProfile != null}"
                androidx.compose.material3.Text(text = statusText, modifier = Modifier.padding(6.dp), color = Color.Black)
            }
        }
    }
}
