package com.codealpha.collegealert.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codealpha.collegealert.data.model.User
import com.codealpha.collegealert.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _user = mutableStateOf<FirebaseUser?>(repository.getCurrentUser())
    val user: State<FirebaseUser?> = _user

    private val _userProfile = mutableStateOf<User?>(null)
    val userProfile: State<User?> = _userProfile

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        _user.value?.let { fetchUserProfile(it.uid) }
    }

    private fun fetchUserProfile(uid: String) {
        viewModelScope.launch {
            _userProfile.value = repository.getUserProfile(uid)
        }
    }

    fun signIn(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.signIn(email, pass)
            result.onSuccess { firebaseUser ->
                _user.value = firebaseUser
                if (firebaseUser != null) {
                    val profile = repository.getUserProfile(firebaseUser.uid)
                    _userProfile.value = profile
                }
                _isLoading.value = false
                onSuccess()
            }.onFailure {
                _error.value = it.message
                _isLoading.value = false
            }
        }
    }

    fun signUp(fullName: String, email: String, pass: String, collegeId: String, isAdmin: Boolean, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.signUp(fullName, email, pass, collegeId, isAdmin)
            result.onSuccess { firebaseUser ->
                _user.value = firebaseUser
                if (firebaseUser != null) {
                    _userProfile.value = User(
                        uid = firebaseUser.uid,
                        fullName = fullName,
                        email = email,
                        collegeId = collegeId,
                        isAdmin = isAdmin
                    )
                }
                _isLoading.value = false
                onSuccess()
            }.onFailure {
                _error.value = it.message
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(updatedUser: User) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.updateUserProfile(updatedUser)
            result.onSuccess {
                _userProfile.value = updatedUser
                _isLoading.value = false
            }.onFailure {
                _error.value = "Failed to update profile"
                _isLoading.value = false
            }
        }
    }

    fun uploadProfilePicture(uri: Uri) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.uploadProfilePicture(uri)
            result.onSuccess { url ->
                _userProfile.value = _userProfile.value?.copy(profilePictureUrl = url)
                _isLoading.value = false
            }.onFailure {
                _error.value = "Failed to upload image"
                _isLoading.value = false
            }
        }
    }

    fun logout(onLogout: () -> Unit) {
        repository.logout()
        _user.value = null
        _userProfile.value = null
        onLogout()
    }
}
