
PS C:\Users\DESIRE\AndroidStudioProjects\CollegeAlert> adb logcat -v time CollegeAlert:D *:S
--------- beginning of main
05-16 14:11:50.070 D/CollegeAlert(11553): [Sat May 16 14:11:50 GMT+03:00 2026] LoginScreen: Sign in pressed for avukiblessingisaac@gmail.com
05-16 14:11:50.078 D/CollegeAlert(11553): [Sat May 16 14:11:50 GMT+03:00 2026] Auth: Attempting signIn for avukiblessingisaac@gmail.com
05-16 14:11:51.768 D/CollegeAlert(11553): [Sat May 16 14:11:51 GMT+03:00 2026] Auth: Fetched profile for PUkxNgP1aGYGth3qWGrR41Ytoly1 isAdmin=false
05-16 14:11:51.768 D/CollegeAlert(11553): [Sat May 16 14:11:51 GMT+03:00 2026] LoginScreen: Sign in success for avukiblessingisaac@gmail.com
05-16 14:11:51.866 D/CollegeAlert(11553): [Sat May 16 14:11:51 GMT+03:00 2026] Navigation: Navigating to main after login
05-16 14:11:51.965 D/CollegeAlert(11553): [Sat May 16 14:11:51 GMT+03:00 2026] MainScreen: Composed MainScreen; userProfile isAdmin=false
05-16 14:11:52.268 D/CollegeAlert(11553): [Sat May 16 14:11:52 GMT+03:00 2026] DashboardScreen: Composing Dashboard; userProfile=PUkxNgP1aGYGth3qWGrR41Ytoly1 isAdmin=false
05-16 14:12:04.589 D/CollegeAlert(11553): [Sat May 16 14:12:04 GMT+03:00 2026] DashboardScreen: Composing Dashboard; userProfile=PUkxNgP1aGYGth3qWGrR41Ytoly1 isAdmin=false
05-16 14:12:47.540 D/CollegeAlert(11553): [Sat May 16 14:12:47 GMT+03:00 2026] Navigation: Logging out and navigating to splash
05-16 14:12:47.573 D/CollegeAlert(11553): [Sat May 16 14:12:47 GMT+03:00 2026] MainScreen: Composed MainScreen; userProfile isAdmin=null
05-16 14:14:26.159 D/CollegeAlert(12507): [Sat May 16 14:14:26 GMT+03:00 2026] Auth: Attempting signUp for rimo@gmail.com isAdmin=true
05-16 14:14:30.970 D/CollegeAlert(12507): [Sat May 16 14:14:30 GMT+03:00 2026] Auth: Created profile for yU1kUs6XPWNN9zMeKRobxLNpBRl2 isAdmin=false
05-16 14:14:31.062 D/CollegeAlert(12507): [Sat May 16 14:14:31 GMT+03:00 2026] Navigation: Navigating to main after signup
05-16 14:14:31.139 D/CollegeAlert(12507): [Sat May 16 14:14:31 GMT+03:00 2026] MainScreen: Composed MainScreen; userProfile isAdmin=false
05-16 14:14:31.344 D/CollegeAlert(12507): [Sat May 16 14:14:31 GMT+03:00 2026] DashboardScreen: Composing Dashboard; userProfile=yU1kUs6XPWNN9zMeKRobxLNpBRl2 isAdmin=false
05-16 14:15:06.619 D/CollegeAlert(12507): [Sat May 16 14:15:06 GMT+03:00 2026] DashboardScreen: Composing Dashboard; userProfile=yU1kUs6XPWNN9zMeKRobxLNpBRl2 isAdmin=false
05-16 14:15:12.930 D/CollegeAlert(12507): [Sat May 16 14:15:12 GMT+03:00 2026] DashboardScreen: Composing Dashboard; userProfile=yU1kUs6XPWNN9zMeKRobxLNpBRl2 isAdmin=false

package com.codealpha.collegealert.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.codealpha.collegealert.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val userProfile by viewModel.userProfile
    val isLoading by viewModel.isLoading
    val context = LocalContext.current

    // Image Picker Launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadProfilePicture(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FB))
            .verticalScroll(rememberScrollState())
    ) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color(0xFFFF9800))
        }

        // Profile Header Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Surface(
                        modifier = Modifier.size(100.dp).clip(CircleShape),
                        color = Color(0xFF1A237E)
                    ) {
                        if (userProfile?.profilePictureUrl != null) {
                            AsyncImage(
                                model = userProfile?.profilePictureUrl,
                                contentDescription = "Profile Picture",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(contentAlignment = Alignment.Center) {
                                Text("👤", fontSize = 48.sp)
                            }
                        }
                    }
                    Surface(
                        modifier = Modifier.align(Alignment.BottomEnd).size(28.dp),
                        shape = CircleShape,
                        color = Color(0xFFFF9800)
                    ) {
                        IconButton(onClick = { launcher.launch("image/*") }) {
                            Icon(Icons.Default.Edit, contentDescription = "Change Picture", tint = Color.White, modifier = Modifier.padding(6.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = userProfile?.fullName ?: "Loading...",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E)
                )

                Text(
                    text = userProfile?.email ?: "",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = {}, label = { Text("ID: ${userProfile?.universityId?.ifEmpty { "N/A" } ?: "N/A"}") })
                    AssistChip(onClick = {}, label = { Text(userProfile?.collegeId ?: "No College") })
                }
                
                if (userProfile?.isAdmin == true) {
                    Spacer(modifier = Modifier.height(8.dp))
                    SuggestionChip(
                        onClick = { /* Handle Admin Mode navigation if needed */ },
                        label = { Text("Administrator Mode") },
                        colors = SuggestionChipDefaults.suggestionChipColors(labelColor = Color(0xFFFF9800))
                    )
                } else {
                    Spacer(modifier = Modifier.height(8.dp))
                    // Debug helper: allow user to promote themselves to admin (will write to Firestore)
                    Button(onClick = {
                        userProfile?.let {
                            viewModel.updateProfile(it.copy(isAdmin = true))
                            Toast.makeText(context, "Requested admin rights (attempting to update profile)", Toast.LENGTH_LONG).show()
                        }
                    }) {
                        Text("Make me Admin (debug)")
                    }
                }

                // Refresh profile button to re-fetch Firestore document (useful after editing in console)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.refreshProfile(); Toast.makeText(context, "Refreshing profile...", Toast.LENGTH_SHORT).show() }) {
                    Text("Refresh Profile")
                }
            }
        }

        // Real functional toggles
        ProfileSection(title = "Notification Preferences") {
            NotificationToggle(
                title = "Exams & Deadlines",
                subtitle = "Urgent alerts for academic schedules",
                checked = userProfile?.examsNotificationsEnabled ?: true,
                onCheckedChange = { enabled ->
                    userProfile?.let { viewModel.updateProfile(it.copy(examsNotificationsEnabled = enabled)) }
                }
            )
            NotificationToggle(
                title = "Campus Fests & Events",
                subtitle = "Updates about social activities",
                checked = userProfile?.festsNotificationsEnabled ?: true,
                onCheckedChange = { enabled ->
                    userProfile?.let { viewModel.updateProfile(it.copy(festsNotificationsEnabled = enabled)) }
                }
            )
            NotificationToggle(
                title = "Security & Safety Alerts",
                subtitle = "Real-time campus broadcasts",
                checked = userProfile?.securityAlertsEnabled ?: true,
                onCheckedChange = { enabled ->
                    userProfile?.let { viewModel.updateProfile(it.copy(securityAlertsEnabled = enabled)) }
                }
            )
        }

        ProfileSection(title = "Account Settings") {
            SettingItem(Icons.Default.Lock, "Privacy & Security", "Manage your data")
            SettingItem(Icons.Default.Language, "App Language", "English (US)")
        }

        ProfileSection(title = "Support") {
            SettingItem(Icons.AutoMirrored.Filled.Help, "Help Center", "Get assistance")
            SettingItem(Icons.Default.Info, "About Challenge Alert", "Version 1.0.0")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Logout Button
        TextButton(
            onClick = { viewModel.logout(onLogout) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        ) {
            Text("LOG OUT", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun ProfileSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A237E), modifier = Modifier.padding(bottom = 8.dp, start = 8.dp))
        Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
            Column { content() }
        }
    }
}

@Composable
fun NotificationToggle(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Medium, color = Color(0xFF1A237E))
            Text(text = subtitle, fontSize = 13.sp, color = Color.Gray)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color(0xFF1A237E))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Medium, color = Color(0xFF1A237E))
            Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
    }
}
