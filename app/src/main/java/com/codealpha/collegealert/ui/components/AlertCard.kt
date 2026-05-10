package com.codealpha.collegealert.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
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
                    color = if (event.category.lowercase() == "notice") Color.Red else Color(0xFF8B5000),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        event.category.uppercase(),
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
                    Text("Just now", fontSize = 10.sp, color = Color.Gray)
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