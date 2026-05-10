package com.codealpha.collegealert.viewmodel

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

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            repository.getEvents().collect { eventList ->
                _events.value = eventList
                _isLoading.value = false
            }
        }
    }
}
