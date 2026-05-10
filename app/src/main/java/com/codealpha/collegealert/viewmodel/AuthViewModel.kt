package com.codealpha.collegealert.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codealpha.collegealert.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _user = mutableStateOf<FirebaseUser?>(repository.getCurrentUser())
    val user: State<FirebaseUser?> = _user

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun signIn(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.signIn(email, pass)
            result.onSuccess {
                _user.value = it
                _isLoading.value = false
                onSuccess()
            }.onFailure {
                _error.value = it.message
                _isLoading.value = false
            }
        }
    }

    fun signUp(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.signUp(email, pass)
            result.onSuccess {
                _user.value = it
                _isLoading.value = false
                onSuccess()
            }.onFailure {
                _error.value = it.message
                _isLoading.value = false
            }
        }
    }

    fun logout(onLogout: () -> Unit) {
        repository.logout()
        _user.value = null
        onLogout()
    }
}