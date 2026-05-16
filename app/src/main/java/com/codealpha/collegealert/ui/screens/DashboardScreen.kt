package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codealpha.collegealert.data.model.Event
import com.codealpha.collegealert.ui.components.AlertCard
import com.codealpha.collegealert.viewmodel.AuthViewModel
import com.codealpha.collegealert.viewmodel.EventViewModel
import com.codealpha.collegealert.util.Logger
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onEventClick: (Event) -> Unit,
    eventViewModel: EventViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val events by eventViewModel.events
    val isLoading by eventViewModel.isLoading
    val userProfile by authViewModel.userProfile
    val context = LocalContext.current
    try { Logger.log(context, "DashboardScreen", "Composing Dashboard; userProfile=${userProfile?.uid} isAdmin=${userProfile?.isAdmin}") } catch (_: Exception) {}

    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Notice", "Seminar", "Exam", "Fest")
    var hasInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(userProfile?.collegeId) {
        val college = userProfile?.collegeId
        if (!college.isNullOrEmpty() && !hasInitialized) {
            eventViewModel.fetchEvents(college)
            hasInitialized = true
        }
    }

    val filteredEvents = if (selectedCategory == "All") {
        events
    } else {
        events.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campus Sentinel", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Menu Action */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A237E),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FB))
                .padding(padding)
        ) {
            if (userProfile == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF1A237E))
                }
            } else if (isLoading && events.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF1A237E))
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))

                        val displayName = userProfile?.fullName ?: "Student"

                        Text(
                            text = "Welcome back,",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = displayName,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A237E),
                            lineHeight = 38.sp
                        )

                        Text(
                            text = "Your campus is monitored at ${userProfile?.collegeId?.ifEmpty { "your institution" } ?: "your institution"}.",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFF9800)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Security, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Security Status", color = Color.White, fontSize = 12.sp)
                                    Text("High Vigilance", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            categories.forEach { category ->
                                FilterChip(
                                    selected = selectedCategory == category,
                                    onClick = { selectedCategory = category },
                                    label = { Text(category) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFF1A237E),
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("High-Priority Alerts", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF1A237E))
                            Spacer(modifier = Modifier.weight(1f))
                            if (filteredEvents.isNotEmpty()) {
                                Text("🚨 Live Updates", color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    if (filteredEvents.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No alerts found for $selectedCategory.", color = Color.Gray)
                            }
                        }
                    } else {
                        items(filteredEvents) { event ->
                            AlertCard(event = event, onClick = { onEventClick(event) })
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    item { Spacer(modifier = Modifier.height(24.dp)) }
                }
            }
        }
    }
}
