package com.codealpha.collegealert.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codealpha.collegealert.data.model.Event
import com.codealpha.collegealert.data.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository = EventRepository()) : ViewModel() {

    private val _events = mutableStateOf<List<Event>>(emptyList())
    val events: State<List<Event>> = _events

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _createEventSuccess = mutableStateOf(false)
    val createEventSuccess: State<Boolean> = _createEventSuccess

    private val _uploadProgress = mutableStateOf<Float?>(null)
    val uploadProgress: State<Float?> = _uploadProgress

    fun fetchEvents(collegeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getEvents(collegeId).collect { eventList ->
                _events.value = eventList
                _isLoading.value = false
            }
        }
    }

    fun createEvent(event: Event, attachmentUri: Uri? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            
            var finalEvent = event
            
            // If there's an attachment, upload it first
            if (attachmentUri != null) {
                val uploadResult = repository.uploadAttachment(attachmentUri, event.attachmentType ?: "file")
                uploadResult.onSuccess { url ->
                    finalEvent = event.copy(attachmentUrl = url)
                }.onFailure {
                    // Handle error if needed
                    _isLoading.value = false
                    return@launch
                }
            }

            val result = repository.createEvent(finalEvent)
            if (result.isSuccess) {
                _createEventSuccess.value = true
            }
            _isLoading.value = false
        }
    }

    fun resetCreateSuccess() {
        _createEventSuccess.value = false
    }
}
