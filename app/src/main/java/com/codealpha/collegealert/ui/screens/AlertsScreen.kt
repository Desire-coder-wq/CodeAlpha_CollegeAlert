package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codealpha.collegealert.data.model.Event
import com.codealpha.collegealert.ui.components.AlertCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(
    onEventClick: (Event) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Information Stream", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FB))
                .padding(padding)
        ) {
            // Priority Banner
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF000051)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = Color(0xFFFF9800))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "You have 3 unread high-priority alerts",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                val alerts = listOf(
                    Event(title = "Emergency: Power Outage", category = "NOTICE", description = "Electrical maintenance in Block C. Power expected to return by 6 PM.", venue = "Block C", time = "1 hour ago"),
                    Event(title = "Room Change: CS101", category = "EXAM", description = "The midterm for CS101 has been moved to Hall B.", venue = "Hall B", time = "2 hours ago"),
                    Event(title = "Flash Mob - Main Square", category = "FEST", description = "Spontaneous cultural performance starting now at the fountain!", venue = "Main Square", time = "30 mins ago")
                )

                items(alerts) { alert ->
                    AlertCard(event = alert, onClick = { onEventClick(alert) })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}