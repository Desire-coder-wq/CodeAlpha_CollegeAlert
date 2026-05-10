package com.codealpha.collegealert.data.repository

import android.net.Uri
import com.codealpha.collegealert.data.model.Event
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class EventRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) {

    // Fetch alerts for a specific college in real-time
    fun getEvents(collegeId: String): Flow<List<Event>> = callbackFlow {
        val subscription = db.collection("events")
            .whereEqualTo("collegeId", collegeId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val events = snapshot.toObjects(Event::class.java)
                    trySend(events)
                }
            }
        awaitClose { subscription.remove() }
    }

    suspend fun createEvent(event: Event): Result<Unit> {
        return try {
            val docRef = db.collection("events").document()
            val newEvent = event.copy(id = docRef.id)
            docRef.set(newEvent).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadAttachment(uri: Uri, type: String): Result<String> {
        return try {
            val fileName = "${type}s/${UUID.randomUUID()}"
            val ref = storage.reference.child(fileName)
            ref.putFile(uri).await()
            val url = ref.downloadUrl.await().toString()
            Result.success(url)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
