package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)), // Deep dark blue
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        // Logo Container
        Box(
            modifier = Modifier
                .size(110.dp)
                .background(Color(0xFFFF9800), shape = androidx.compose.foundation.shape.CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🛡️",
                fontSize = 52.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Campus Sentinel",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = "ACADEMIC VIGILANCE & SAFETY",
            fontSize = 14.sp,
            letterSpacing = 4.sp,
            color = Color(0xFF94A3B8),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Welcome Message
        Text(
            text = "Never miss a beat on campus.",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Real-time alerts for exams, fests, seminars & important notices.",
            fontSize = 16.sp,
            color = Color(0xFFCBD5E1),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Quick Stats
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(title = "12k+", label = "Students")
            StatItem(title = "98%", label = "Alert Delivery")
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun StatItem(title: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9800)
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF94A3B8)
        )
    }
}