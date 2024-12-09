package com.jostin.gestionacceso.domain.models
import com.google.firebase.Timestamp

data class Laboratory(
    val id: String = "",
    val name: String = "",
    val code: String = "",
    val capacity: Int = 0,
    val status: String = "available", // 'available' | 'in-use' | 'maintenance'
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)
