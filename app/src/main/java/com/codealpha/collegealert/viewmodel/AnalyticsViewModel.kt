package com.codealpha.collegealert.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AnalyticsViewModel(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) : ViewModel() {

    private val _eventsCount = mutableStateOf(0)
    val eventsCount: State<Int> = _eventsCount

    private val _usersCount = mutableStateOf(0)
    val usersCount: State<Int> = _usersCount

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadCounts(collegeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val eventsSnap = db.collection("events").whereEqualTo("collegeId", collegeId).get().await()
                _eventsCount.value = eventsSnap.size()
            } catch (_: Exception) {
                _eventsCount.value = 0
            }
            try {
                val usersSnap = db.collection("users").whereEqualTo("collegeId", collegeId).get().await()
                _usersCount.value = usersSnap.size()
            } catch (_: Exception) {
                _usersCount.value = 0
            }
            _isLoading.value = false
        }
    }
}

