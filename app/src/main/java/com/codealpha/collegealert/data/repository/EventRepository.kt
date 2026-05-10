package com.codealpha.collegealert.data.repository

import com.codealpha.collegealert.data.model.Event
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class EventRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    // Fetch alerts in real-time
    fun getEvents(): Flow<List<Event>> = callbackFlow {
        val subscription = db.collection("events")
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
}
