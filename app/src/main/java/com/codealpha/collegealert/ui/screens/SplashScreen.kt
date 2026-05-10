package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {
        delay(3000)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            navController.navigate("main") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    // Premium gradient background (deep navy to rich indigo)
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0A1428),  // Deep navy top
            Color(0xFF1A1A3A),  // Mid indigo
            Color(0xFF0D1B2A)   // Dark blue bottom
        ),
        startY = 0.0f,
        endY = Float.POSITIVE_INFINITY
    )

    // Subtle accent gradient for brand glow
    val accentGlow = Brush.radialGradient(
        colors = listOf(
            Color(0x33FF9800),  // Transparent orange glow
            Color(0x000A1428)    // Fully transparent
        ),
        center = Offset(500f, 300f),
        radius = 800f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .background(accentGlow),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Container with gradient border effect
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(
                        color = Color(0xFFFF9800),
                        shape = RoundedCornerShape(28.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎓",
                    fontSize = 72.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Main Title with subtle shadow
            Text(
                text = "Campus Sentinel",
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle with divider lines effect
            Text(
                text = "ACADEMIC VIGILANCE & SAFETY",
                fontSize = 14.sp,
                letterSpacing = 4.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFFF9800).copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(100.dp))

            // Footer Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("🛡️", fontSize = 24.sp)
                    Text("📢", fontSize = 24.sp)
                    Text("✅", fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "© 2026 University Security Division",
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = Color(0xFF5A6B8A),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}