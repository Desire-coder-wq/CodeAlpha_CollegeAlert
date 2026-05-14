package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.codealpha.collegealert.R
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1428)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // NEW LOGO IMPLEMENTATION
            Image(
                painter = painterResource(id = R.drawable.ic_app_logo),
                contentDescription = "Challenge Alert Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "CHALLENGE ALERT",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "VIGILANCE • SAFETY • SUCCESS",
                fontSize = 12.sp,
                letterSpacing = 4.sp,
                color = Color(0xFFFF9800),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(100.dp))

            Row(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("🛡️", fontSize = 24.sp)
                Text("⚡", fontSize = 24.sp)
                Text("🔔", fontSize = 24.sp)
            }
        }
    }
}