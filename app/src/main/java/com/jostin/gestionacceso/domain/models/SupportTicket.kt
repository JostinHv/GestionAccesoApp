package com.jostin.gestionacceso.domain.models
import com.google.firebase.Timestamp

data class SupportTicket(
    val id: String = "",
    val userId: String = "", // Referencia a User
    val subject: String = "",
    val message: String = "",
    val status: String = "open", // 'open' | 'in-progress' | 'closed'
    val adminResponse: String? = null,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)
