package com.codealpha.collegealert.data.repository

import android.net.Uri
import com.codealpha.collegealert.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.Source
import com.codealpha.collegealert.util.Logger
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
                val docRef = db.collection("users").document(firebaseUser.uid)
                // Write the profile
                docRef.set(userProfile).await()
                // Read back what was written and ensure isAdmin stored correctly; some setups or rules may strip fields
                val snapshot = docRef.get().await()
                val stored = snapshot.data
                try { Logger.log(null, "AuthRepo", "Wrote user profile; snapshot.data=${stored}") } catch (_: Exception) {}
                // If the stored document does not reflect the requested isAdmin, attempt to explicitly set the field
                val storedIsAdmin = snapshot.getBoolean("isAdmin")
                if (storedIsAdmin == null || storedIsAdmin != isAdmin) {
                    // Try to update the single field to ensure it's present
                    docRef.update("isAdmin", isAdmin).await()
                    try { Logger.log(null, "AuthRepo", "Updated isAdmin to $isAdmin for ${firebaseUser.uid}") } catch (_: Exception) {}
                }
            }
            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(uid: String): User? {
        return try {
            // Force server source to avoid returning stale cached documents
            val snapshot = db.collection("users").document(uid).get(Source.SERVER).await()
            val data = snapshot.data
            try { Logger.log(null, "AuthRepo", "getUserProfile snapshot=${data}") } catch (_: Exception) {}

            // Build the User manually to be tolerant of field name differences (isAdmin vs admin)
            val fullName = snapshot.getString("fullName") ?: snapshot.getString("name") ?: ""
            val email = snapshot.getString("email") ?: ""
            val collegeId = snapshot.getString("collegeId") ?: snapshot.getString("college") ?: ""
            val profilePictureUrl = snapshot.getString("profilePictureUrl")
            val universityId = snapshot.getString("universityId") ?: ""

            // Support both 'isAdmin' and 'admin' keys and defensive typing
            val isAdminAny = when {
                data == null -> null
                data.containsKey("isAdmin") -> data["isAdmin"]
                data.containsKey("admin") -> data["admin"]
                else -> null
            }
            val isAdmin = when (isAdminAny) {
                is Boolean -> isAdminAny
                is String -> isAdminAny.toBoolean()
                is Number -> isAdminAny.toInt() != 0
                else -> false
            }

            User(
                uid = uid,
                fullName = fullName,
                email = email,
                universityId = universityId,
                collegeId = collegeId,
                profilePictureUrl = profilePictureUrl,
                isAdmin = isAdmin
            )
        } catch (e: Exception) {
            try { Logger.log(null, "AuthRepo", "getUserProfile failed: ${e.message}") } catch (_: Exception) {}
            null
        }
    }

    suspend fun updateUserProfile(user: User): Result<Unit> {
        return try {
            val docRef = db.collection("users").document(user.uid)
            docRef.set(user).await()
            // Log the result by reading back
            val snapshot = docRef.get().await()
            try { Logger.log(null, "AuthRepo", "updateUserProfile wrote: ${snapshot.data}") } catch (_: Exception) {}
            Result.success(Unit)
        } catch (e: Exception) {
            try { Logger.log(null, "AuthRepo", "updateUserProfile failed: ${e.message}") } catch (_: Exception) {}
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
