package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1428)), // Deep premium blue
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Container - Matching your image
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(
                        color = Color(0xFFFF9800),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎓",
                    fontSize = 72.sp
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Campus Sentinel",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "ACADEMIC VIGILANCE & SAFETY",
                fontSize = 15.sp,
                letterSpacing = 4.sp,
                color = Color(0xFF94A3B8),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(120.dp))

            // Bottom line with icons (matching design)
            Row(
                modifier = Modifier.fillMaxWidth(0.6f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("🛡️", fontSize = 28.sp, color = Color(0xFF64748B))
                Text("📢", fontSize = 28.sp, color = Color(0xFFFF9800))
                Text("✅", fontSize = 28.sp, color = Color(0xFF64748B))
            }
        }
    }
}