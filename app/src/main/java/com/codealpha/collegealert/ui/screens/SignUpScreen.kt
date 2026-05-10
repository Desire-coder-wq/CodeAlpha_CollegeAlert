package com.codealpha.collegealert.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

    val isFullNameValid = fullName.isNotBlank()
    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isCollegeValid = collegeId.length >= 3
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
        Spacer(modifier = Modifier.height(40.dp))

        Surface(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFF1A237E)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("🛡️", fontSize = 32.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Join Campus Sentinel", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
        Text(text = "Create your account for real-time alerts.", fontSize = 14.sp, color = Color.Gray)

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
                    label = { Text("Alex Rivers") },
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = textFieldColors,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("COLLEGE NAME", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                OutlinedTextField(
                    value = collegeId,
                    onValueChange = { collegeId = it },
                    label = { Text("e.g. Stanford University") },
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = textFieldColors,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("UNIVERSITY EMAIL", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("alex@university.edu") },
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = textFieldColors,
                    isError = email.isNotEmpty() && !isEmailValid,
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // ADMIN CHECKBOX IS HERE - MOVE TO A PROMINENT PLACE
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isAdmin,
                        onCheckedChange = { isAdmin = it },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1A237E))
                    )
                    Text("Register as Administrator", fontSize = 14.sp, color = Color(0xFF1A237E), fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("PASSWORD", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Min 6 characters") },
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = textFieldColors,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null, tint = Color(0xFF1A237E))
                        }
                    },
                    singleLine = true
                )

                if (error != null) {
                    Text(text = error!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { 
                        if (canSubmit) {
                            viewModel.signUp(fullName, email, password, collegeId, isAdmin, onSignUpSuccess)
                        } else {
                            Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000051)),
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

        Spacer(modifier = Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account? ", color = Color.Gray)
            TextButton(onClick = onBackToLogin) {
                Text("Sign In", color = Color(0xFF1A237E), fontWeight = FontWeight.Bold)
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}
