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
import com.codealpha.collegealert.data.model.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    event: Event, // Now taking the actual event object
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
            // Hero Image or Attachment
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(Color.DarkGray)
            ) {
                if (event.attachmentUrl != null) {
                    // In a real app, use Coil here to load the URL
                    Text("📷 Attachment Loaded", modifier = Modifier.align(Alignment.Center), color = Color.White)
                } else {
                    Text(
                        text = "📢 ${event.category.uppercase()}",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Tag
                Surface(
                    modifier = Modifier.padding(16.dp).align(Alignment.TopStart),
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFFF9800)
                ) {
                    Text(
                        text = event.category.uppercase(),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Text(
                    text = event.title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Info Rows
                InfoRow(icon = Icons.Default.CalendarToday, label = "Date", value = "Upcoming")
                InfoRow(icon = Icons.Default.AccessTime, label = "Time", value = event.time)
                InfoRow(icon = Icons.Default.LocationOn, label = "Venue", value = event.venue)

                Spacer(modifier = Modifier.height(24.dp))

                Text("DESCRIPTION", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 12.sp)
                Text(
                    text = event.description,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF455A64),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // MAP SECTION
                Text("LOCATION MAP", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 12.sp)
                Card(
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(top = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1))
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        if (event.latitude != null) {
                            Text("📍 Map View [${event.latitude}, ${event.longitude}]", color = Color(0xFF1A237E))
                        } else {
                            Text("No location tagged for this alert", color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = { /* Add to Calendar Logic */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Add to My Schedule")
                }
            }
        }
    }
}

@Composable
private fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF1A237E), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Text(text = value, fontWeight = FontWeight.Medium, color = Color(0xFF1A237E))
        }
    }
}
