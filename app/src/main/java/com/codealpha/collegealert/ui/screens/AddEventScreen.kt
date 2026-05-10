package com.codealpha.collegealert.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codealpha.collegealert.data.model.Event
import com.codealpha.collegealert.viewmodel.AuthViewModel
import com.codealpha.collegealert.viewmodel.EventViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    onBackClick: () -> Unit,
    eventViewModel: EventViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Notice") }
    var venue by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    
    val context = LocalContext.current
    val userProfile by authViewModel.userProfile
    val createSuccess by eventViewModel.createEventSuccess

    val inputColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color(0xFF1A237E),
        unfocusedTextColor = Color(0xFF1A237E),
        focusedLabelColor = Color(0xFF1A237E),
        unfocusedLabelColor = Color.Gray,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
    )

    LaunchedEffect(createSuccess) {
        if (createSuccess) {
            Toast.makeText(context, "Alert Published!", Toast.LENGTH_SHORT).show()
            eventViewModel.resetCreateSuccess()
            onBackClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create New Alert", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A237E),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FB))
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("ALERT DETAILS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Event Title") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = inputColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Full Description") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(8.dp),
                colors = inputColors
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("CATEGORY", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            val categories = listOf("Notice", "Exam", "Fest", "Seminar")
            var expanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true).fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = inputColors
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    categories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, color = Color(0xFF1A237E)) },
                            onClick = {
                                category = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = inputColors,
                    singleLine = true
                )
                OutlinedTextField(
                    value = venue,
                    onValueChange = { venue = it },
                    label = { Text("Venue") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = inputColors,
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (title.isBlank() || description.isBlank()) {
                        Toast.makeText(context, "Please fill required fields", Toast.LENGTH_SHORT).show()
                    } else {
                        val newEvent = Event(
                            title = title,
                            description = description,
                            category = category,
                            time = time,
                            venue = venue,
                            collegeId = userProfile?.collegeId ?: "General",
                            createdAt = Date()
                        )
                        eventViewModel.createEvent(newEvent)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Publish Alert Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
