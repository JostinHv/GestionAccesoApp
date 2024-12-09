package com.jostin.gestionacceso.domain.models
import com.google.firebase.Timestamp

data class Course(
    val id: String = "",
    val name: String = "",
    val code: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)
