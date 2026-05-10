package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codealpha.collegealert.data.model.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onEventClick: (Event) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campus Sentinel", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* Menu */ }) {
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
        // Bottom Navigation Bar is now ONLY in MainScreen.kt to avoid double bars
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FB))
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Welcome back,\nAlex",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E)
                )
                Text(
                    text = "Your campus is monitored. Stay informed about active seminars, upcoming exams, and critical safety updates.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(
                    onClick = { /* View Reports */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("View Active Reports")
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Security Status Card
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
                        Surface(
                            color = Color(0xFFE65100),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                "ACTIVE",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Filters
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(selected = true, onClick = {}, label = { Text("All") })
                    FilterChip(selected = false, onClick = {}, label = { Text("Seminars") })
                    FilterChip(selected = false, onClick = {}, label = { Text("Exams") })
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("High-Priority Alerts", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF1A237E))
                    Spacer(modifier = Modifier.weight(1f))
                    Text("🚨 Live Updates", color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Sample Data
            val sampleEvents = listOf(
                Event(title = "Water Supply Maintenance: Block B", category = "NOTICE", description = "Critical maintenance of the main reservoir will commence shortly. Expect total shutdown of water supply in Residential Block B for the next 4 hours.", venue = "Block B, East Wing", time = "14:00 - 18:00"),
                Event(title = "AI Ethics in Modern Research", category = "SEMINAR", description = "Dr. Helena Vance discusses the profound implications of large language models in academic integrity.", venue = "Main Auditorium", time = "16:30 Today")
            )

            items(sampleEvents) { event ->
                AlertCard(event = event, onClick = { onEventClick(event) })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AlertCard(event: Event, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // Placeholder for Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray)
            ) {
                Surface(
                    modifier = Modifier.padding(8.dp),
                    color = if (event.category == "NOTICE") Color.Red else Color(0xFF8B5000),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        event.category,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(event.title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A237E), modifier = Modifier.weight(1f))
                    Text("2 mins ago", fontSize = 10.sp, color = Color.Gray)
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                Text(event.description, fontSize = 14.sp, color = Color.DarkGray, maxLines = 3)
                
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime, 
                        contentDescription = null, 
                        modifier = Modifier.size(14.dp), 
                        tint = Color.Gray
                    )
                    Text(" " + event.time, fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = Icons.Default.LocationOn, 
                        contentDescription = null, 
                        modifier = Modifier.size(14.dp), 
                        tint = Color.Gray
                    )
                    Text(" " + event.venue, fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}
