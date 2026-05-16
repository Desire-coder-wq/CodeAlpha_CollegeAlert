package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codealpha.collegealert.viewmodel.AnalyticsViewModel
import com.codealpha.collegealert.viewmodel.AuthViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(onBackClick: () -> Unit, authViewModel: AuthViewModel = viewModel(), analyticsViewModel: AnalyticsViewModel = viewModel()) {
    val userProfile by authViewModel.userProfile
    val isLoading by analyticsViewModel.isLoading
    val eventsCount by analyticsViewModel.eventsCount
    val usersCount by analyticsViewModel.usersCount

    // Trigger load when profile becomes available
    androidx.compose.runtime.LaunchedEffect(userProfile?.collegeId) {
        val collegeId = userProfile?.collegeId ?: ""
        analyticsViewModel.loadCounts(collegeId)
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Analytics", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBackClick) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A237E), titleContentColor = Color.White)
        )
    }) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding), contentAlignment = Alignment.TopCenter) {
            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFF1A237E))
            } else {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("ENGAGEMENT SUMMARY", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Total Events", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(eventsCount.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Registered Users", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(usersCount.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                        }
                    }
                }
            }
        }
    }
}


