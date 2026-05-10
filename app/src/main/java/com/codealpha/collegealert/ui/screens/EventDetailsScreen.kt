package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alert Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A237E),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FB))
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color.Gray)
            ) {
                Text(
                    text = "📸 Tech Fest Image",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )

                // Tag
                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart),
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFFF9800)
                ) {
                    Text(
                        text = "CAMPUS EVENT",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Tech Fest 2024",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E)
                )

                Text(
                    text = "Posted 2 hours ago",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Info Cards
                InfoRow(icon = Icons.Default.CalendarToday, label = "Date", value = "Oct 15, 2024")
                InfoRow(icon = Icons.Default.AccessTime, label = "Time", value = "10:00 AM - 4:00 PM")
                InfoRow(icon = Icons.Default.LocationOn, label = "Venue", value = "Main Auditorium, Block 4")

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "EVENT DESCRIPTION",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Join us for the most anticipated annual technology gathering. Tech Fest 2024 brings together students, industry leaders, and innovators for a day of demonstrations and coding marathons.",
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF455A64)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Action Buttons
                Button(
                    onClick = { /* Add to Calendar */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add to Calendar")
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { /* View Map */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("View Venue Map", color = Color(0xFF1A237E))
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Speaker Lineup
                Text(
                    text = "Speaker Lineup",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1A237E)
                )

                Spacer(modifier = Modifier.height(16.dp))

                SpeakerItem("Dr. Sarah Chen", "CTO, Neural Dynamics")
                SpeakerItem("Prof. James Miller", "Dept. Head, Computer Science")

                Spacer(modifier = Modifier.height(32.dp))

                // Important Note
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFF9800)),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("ℹ️", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Important",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Please bring your student ID card for entry verification and workshop registration. Doors open at 09:30 AM.",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF1A237E))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 14.sp, color = Color.Gray)
            Text(text = value, fontWeight = FontWeight.Medium, color = Color(0xFF1A237E))
        }
    }
}

@Composable
fun SpeakerItem(name: String, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF1A237E), RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Text("👤", color = Color.White)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = name, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A237E))
            Text(text = title, fontSize = 14.sp, color = Color.Gray)
        }
    }
}