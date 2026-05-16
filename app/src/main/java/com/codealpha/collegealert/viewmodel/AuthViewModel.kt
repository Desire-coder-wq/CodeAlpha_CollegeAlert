package com.codealpha.collegealert.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codealpha.collegealert.data.model.User
import com.codealpha.collegealert.data.repository.AuthRepository
import com.codealpha.collegealert.util.Logger
import androidx.compose.ui.platform.LocalContext
import android.content.Context
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
            // Log start of sign-in
            try {
                Logger.log(null, "Auth", "Attempting signIn for $email")
            } catch (_: Exception) {}
            val result = repository.signIn(email, pass)
            result.onSuccess { firebaseUser ->
                _user.value = firebaseUser
                if (firebaseUser != null) {
                    // Fetch authoritative profile from Firestore to ensure isAdmin and other fields are up-to-date
                    val profile = repository.getUserProfile(firebaseUser.uid)
                    _userProfile.value = profile
                    try { Logger.log(null, "Auth", "Fetched profile for ${firebaseUser.uid} isAdmin=${profile?.isAdmin}") } catch (_: Exception) {}
                }
                _isLoading.value = false
                onSuccess()
            }.onFailure {
                try { Logger.log(null, "Auth", "signIn failed: ${it.message}") } catch (_: Exception) {}
                _error.value = it.message
                _isLoading.value = false
            }
        }
    }

    fun signUp(fullName: String, email: String, pass: String, collegeId: String, isAdmin: Boolean, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try { Logger.log(null, "Auth", "Attempting signUp for $email isAdmin=$isAdmin") } catch (_: Exception) {}
            val result = repository.signUp(fullName, email, pass, collegeId, isAdmin)
            result.onSuccess { firebaseUser ->
                _user.value = firebaseUser
                if (firebaseUser != null) {
                    // After creating the auth user, fetch the saved Firestore profile to ensure consistency
                    val profile = repository.getUserProfile(firebaseUser.uid)
                    if (profile != null) {
                        _userProfile.value = profile
                        try { Logger.log(null, "Auth", "Created profile for ${firebaseUser.uid} isAdmin=${profile.isAdmin}") } catch (_: Exception) {}
                    } else {
                        // Firestore read may be blocked by rules or network. Use a best-effort local profile so admin UI appears immediately.
                        _userProfile.value = com.codealpha.collegealert.data.model.User(
                            uid = firebaseUser.uid,
                            fullName = fullName,
                            email = email,
                            collegeId = collegeId,
                            isAdmin = isAdmin
                        )
                        try { Logger.log(null, "Auth", "Firestore profile missing; using local fallback for ${firebaseUser.uid} isAdmin=$isAdmin") } catch (_: Exception) {}
                    }
                }
                _isLoading.value = false
                onSuccess()
            }.onFailure {
                try { Logger.log(null, "Auth", "signUp failed: ${it.message}") } catch (_: Exception) {}
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
                try { Logger.log(null, "Auth", "updateProfile success for ${updatedUser.uid} isAdmin=${updatedUser.isAdmin}") } catch (_: Exception) {}
                _isLoading.value = false
            }.onFailure {
                _error.value = "Failed to update profile"
                try { Logger.log(null, "Auth", "updateProfile failed: ${it.message}") } catch (_: Exception) {}
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

    // Public helper to refresh the user profile from Firestore (useful after manual edits in console)
    fun refreshProfile() {
        val uid = _user.value?.uid
        if (uid != null) fetchUserProfile(uid)
    }
}
