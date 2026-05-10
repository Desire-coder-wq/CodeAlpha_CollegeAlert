package com.codealpha.collegealert.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    
    // Extensible Features State
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var attachmentType by remember { mutableStateOf<String?>(null) }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    
    val context = LocalContext.current
    val userProfile by authViewModel.userProfile
    val createSuccess by eventViewModel.createEventSuccess
    val isLoading by eventViewModel.isLoading

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedUri = uri
        attachmentType = "image" // Defaulting to image for simplicity, can be dynamic
    }

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

            Text("EXTensible FEATURES", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { launcher.launch("*/*") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8EAF6), contentColor = Color(0xFF1A237E)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.AttachFile, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (selectedUri != null) "File Selected" else "Add File")
                }
                
                Button(
                    onClick = { 
                        // Mocking location selection
                        latitude = 0.3476
                        longitude = 32.5825
                        Toast.makeText(context, "Location tagged!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8EAF6), contentColor = Color(0xFF1A237E)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (latitude != null) "Located" else "Tag Map")
                }
            }

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
                            latitude = latitude,
                            longitude = longitude,
                            attachmentType = attachmentType,
                            createdAt = Date()
                        )
                        eventViewModel.createEvent(newEvent, selectedUri)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Icon(Icons.Default.CloudUpload, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Publish Alert Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
