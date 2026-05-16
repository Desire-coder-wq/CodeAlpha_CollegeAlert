package com.codealpha.collegealert.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.verticalScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.codealpha.collegealert.R
import com.codealpha.collegealert.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onBackToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var collegeId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isAdmin by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    val isFullNameValid = fullName.trim().isNotBlank() && fullName.length >= 2
    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isCollegeValid = collegeId.trim().isNotBlank() && collegeId.length >= 2
    val isPasswordValid = password.length >= 6
    val canSubmit = isFullNameValid && isEmailValid && isCollegeValid && isPasswordValid

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color(0xFF1A237E),
        unfocusedTextColor = Color(0xFF1A237E),
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        cursorColor = Color(0xFF1A237E),
        focusedBorderColor = Color(0xFF1A237E),
        unfocusedBorderColor = Color.LightGray,
        focusedLabelColor = Color(0xFF1A237E),
        unfocusedLabelColor = Color.Gray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FB))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Logo
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = R.drawable.ic_challenge_alert_logo,
                contentDescription = "Challenge Alert Logo",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Join Challenge Alert",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("FULL NAME", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = { Text("John Doe") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    isError = fullName.isNotEmpty() && !isFullNameValid,
                    singleLine = true,
                    colors = textFieldColors
                )
                if (fullName.isNotEmpty() && !isFullNameValid) {
                    Text("Name must be at least 2 characters", color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("COLLEGE NAME", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                OutlinedTextField(
                    value = collegeId,
                    onValueChange = { collegeId = it },
                    placeholder = { Text("Stanford University") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    isError = collegeId.isNotEmpty() && !isCollegeValid,
                    singleLine = true,
                    colors = textFieldColors
                )
                if (collegeId.isNotEmpty() && !isCollegeValid) {
                    Text("College name must be at least 2 characters", color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("UNIVERSITY EMAIL", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("john@university.edu") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    isError = email.isNotEmpty() && !isEmailValid,
                    singleLine = true,
                    colors = textFieldColors
                )
                if (email.isNotEmpty() && !isEmailValid) {
                    Text("Invalid email format", color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("PASSWORD", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Min 6 characters") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null, tint = Color(0xFF1A237E))
                        }
                    },
                    isError = password.isNotEmpty() && !isPasswordValid,
                    singleLine = true,
                    colors = textFieldColors
                )
                if (password.isNotEmpty() && !isPasswordValid) {
                    Text("Password must be at least 6 characters", color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Admin Toggle with Better Styling
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = if (isAdmin) Color(0xFFE8EAF6) else Color(0xFFF5F5F5)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Checkbox(
                            checked = isAdmin,
                            onCheckedChange = { isAdmin = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1A237E))
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Register as Administrator", fontSize = 14.sp, color = Color(0xFF1A237E), fontWeight = FontWeight.SemiBold)
                            Text("Can create and manage alerts", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }

                if (error != null) {
                    Text(text = "❌ ${error!!}", color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 12.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (canSubmit) {
                            viewModel.signUp(fullName, email, password, collegeId, isAdmin, onSignUpSuccess)
                        } else {
                            Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !isLoading && canSubmit
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("CREATE ACCOUNT  →", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account? ", color = Color.Gray)
            TextButton(onClick = onBackToLogin) {
                Text("Sign In", color = Color(0xFF1A237E), fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}