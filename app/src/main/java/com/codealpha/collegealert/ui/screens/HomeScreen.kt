package com.codealpha.collegealert.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(onGetStartedClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FB))
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CAMPUS SENTINEL",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A237E),
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Main Headline
        Text(
            text = "Never miss a beat\non campus.",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E),
            lineHeight = 48.sp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Get real-time alerts for exams, fests, and seminars directly on your phone.\nStay informed with an authoritative information stream designed for your success.",
            fontSize = 16.sp,
            color = Color(0xFF455A64),
            modifier = Modifier.padding(horizontal = 24.dp),
            lineHeight = 26.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Category Cards
        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CategoryCard(title = "Exams", emoji = "📅", modifier = Modifier.weight(1f))
            CategoryCard(title = "Fests", emoji = "🎉", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Buttons
        Button(
            onClick = onGetStartedClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(58.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000051)),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Get Started  →", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedButton(
            onClick = { /* Learn More */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(58.dp),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1A237E))
        ) {
            Text("Learn More", color = Color(0xFF1A237E), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Social Proof
        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("👥", fontSize = 22.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "12k+ Students already joined",
                fontSize = 15.sp,
                color = Color(0xFF607D8B),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Footer Area
        Column(modifier = Modifier.fillMaxWidth()) {
            // Hero Placeholder
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .height(160.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1))
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Illustration Area\n[URGENT ALERT PREVIEW]",
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            // The Bottom Footer Bar from your image
            Divider(modifier = Modifier.padding(top = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FooterItem("System Online", hasDot = true)
                FooterDivider()
                FooterItem("Vigilant Monitoring")
                FooterDivider()
                FooterItem("Privacy Policy")
                FooterDivider()
                FooterItem("Support")
            }
        }
    }
}

@Composable
fun FooterItem(text: String, hasDot: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (hasDot) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(Color(0xFF8B5000), CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(text = text, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun FooterDivider() {
    Box(modifier = Modifier.width(1.dp).height(12.dp).background(Color.LightGray))
}

@Composable
fun CategoryCard(title: String, emoji: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1A237E))
        }
    }
}