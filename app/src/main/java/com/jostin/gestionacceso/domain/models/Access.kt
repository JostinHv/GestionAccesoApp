package com.jostin.gestionacceso.domain.models

import com.google.firebase.Timestamp

data class Access(
    val id: String = "",
    val userName: String = "", // Referencia a User
    val labName: String = "", // Nombre del laboratorio
    val courseName: String = "", // Nombre del curso
    val cardId: String = "", // Referencia a Card
    val entryDay: Timestamp = Timestamp.now(),
    val entryTime: Timestamp = Timestamp.now(),
    val exitTime: Timestamp? = null,
    val status: String = "active", // 'active' | 'completed'
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)

