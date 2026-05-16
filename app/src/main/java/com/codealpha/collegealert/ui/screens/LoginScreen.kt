package com.codealpha.collegealert.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.codealpha.collegealert.R
import com.codealpha.collegealert.viewmodel.AuthViewModel
import com.codealpha.collegealert.util.Logger

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= 6

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color(0xFF1A237E),
        unfocusedTextColor = Color(0xFF1A237E),
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        cursorColor = Color(0xFF1A237E),
        focusedBorderColor = Color(0xFF1A237E),
        unfocusedBorderColor = Color.LightGray,
        focusedLabelColor = Color(0xFF1A237E),
        unfocusedLabelColor = Color.Gray,
        errorTextColor = Color.Red
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

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
            text = "Challenge Alert",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("UNIVERSITY EMAIL", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("alex@university.edu", color = Color.LightGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    isError = email.isNotEmpty() && !isEmailValid,
                    singleLine = true,
                    colors = textFieldColors
                )

                if (email.isNotEmpty() && !isEmailValid) {
                    Text("Invalid email format", color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("PASSWORD", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("••••••••", color = Color.LightGray) },
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
                    singleLine = true,
                    colors = textFieldColors
                )

                if (password.isNotEmpty() && !isPasswordValid) {
                    Text("Password must be at least 6 characters", color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                }

                if (error != null) {
                    Text(text = "❌ ${error!!}", color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 12.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (isEmailValid && isPasswordValid) {
                                try { Logger.log(context, "LoginScreen", "Sign in pressed for $email") } catch (_: Exception) {}
                                viewModel.signIn(email, password) {
                                    try { Logger.log(context, "LoginScreen", "Sign in success for $email") } catch (_: Exception) {}
                                    Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show()
                                    onLoginSuccess()
                                }
                        } else {
                            Toast.makeText(context, "Please check email and password", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !isLoading && (isEmailValid && isPasswordValid)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("SIGN IN  →", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("New to the community? ", color = Color.Gray)
            TextButton(onClick = onSignUpClick) {
                Text("Sign Up", color = Color(0xFF1A237E), fontWeight = FontWeight.Bold)
            }
        }
    }
}