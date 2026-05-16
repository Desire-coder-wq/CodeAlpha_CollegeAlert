package com.codealpha.collegealert.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

class BroadcastRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    suspend fun createBroadcast(title: String, message: String, collegeId: String, createdBy: String): Result<Unit> {
        return try {
            val docRef = db.collection("broadcasts").document()
            val payload = mapOf(
                "id" to docRef.id,
                "title" to title,
                "message" to message,
                "collegeId" to collegeId,
                "createdBy" to createdBy,
                "createdAt" to Date()
            )
            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

