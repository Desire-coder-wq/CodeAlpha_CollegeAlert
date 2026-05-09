package com.codealpha.collegealert.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",        // Seminar, Exam, Fest, Notice
    val date: Date? = null,
    val time: String = "",
    val venue: String = "",
    val organizer: String = "College Admin",

    @ServerTimestamp
    val createdAt: Date? = null
) {
    fun getCategoryColor(): Long {
        return when (category.lowercase()) {
            "exam" -> 0xFFEF5350
            "fest" -> 0xFF66BB6A
            "seminar" -> 0xFF42A5F5
            "notice" -> 0xFFFFCA28
            else -> 0xFF9E9E9E
        }
    }
}