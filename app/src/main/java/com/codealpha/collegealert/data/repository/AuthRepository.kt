package com.codealpha.collegealert.data.repository

import android.net.Uri
import com.codealpha.collegealert.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) {

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    suspend fun signIn(email: String, pass: String): Result<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, pass).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(fullName: String, email: String, pass: String, collegeId: String, isAdmin: Boolean): Result<FirebaseUser?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                // Save additional user data to Firestore
                val userProfile = User(
                    uid = firebaseUser.uid,
                    fullName = fullName,
                    email = email,
                    collegeId = collegeId,
                    isAdmin = isAdmin
                )
                db.collection("users").document(firebaseUser.uid).set(userProfile).await()
            }
            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(uid: String): User? {
        return try {
            db.collection("users").document(uid).get().await().toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateUserProfile(user: User): Result<Unit> {
        return try {
            db.collection("users").document(user.uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadProfilePicture(uri: Uri): Result<String> {
        return try {
            val uid = auth.currentUser?.uid ?: throw Exception("Not logged in")
            val ref = storage.reference.child("profiles/$uid.jpg")
            ref.putFile(uri).await()
            val url = ref.downloadUrl.await().toString()
            
            // Update Firestore with the new URL
            db.collection("users").document(uid).update("profilePictureUrl", url).await()
            
            Result.success(url)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }
}
