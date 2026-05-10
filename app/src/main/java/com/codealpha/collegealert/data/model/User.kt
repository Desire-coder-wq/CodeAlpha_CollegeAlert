package com.codealpha.collegealert.data.model

data class User(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val universityId: String = "",
    val collegeId: String = "", // Used to filter alerts for specific schools
    val profilePictureUrl: String? = null,
    val examsNotificationsEnabled: Boolean = true,
    val festsNotificationsEnabled: Boolean = true,
    val securityAlertsEnabled: Boolean = true
)
