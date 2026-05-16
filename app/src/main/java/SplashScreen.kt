package com.codealpha.collegealert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.codealpha.collegealert.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // App logo
        Box(
            modifier = Modifier.size(140.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = com.codealpha.collegealert.R.drawable.ic_app_logo,
                contentDescription = "App Logo",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "COLLEGE ALERT",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            letterSpacing = 2.sp
        )

        Text(
            text = "CAMPUS VIGILANCE & SAFETY",
            fontSize = 14.sp,
            color = Color(0xFF94A3B8),
            letterSpacing = 2.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Stay Informed • Stay Safe",
            fontSize = 16.sp,
            color = Color(0xFF64748B)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Loading indicator
        Text(
            text = "Loading...",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 40.dp)
        )
    }
}