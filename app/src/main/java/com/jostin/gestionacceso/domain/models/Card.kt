package com.jostin.gestionacceso.domain.models

import com.google.firebase.Timestamp

data class Card(
    val id: String = "",
    val cardNumber: String = "",
    val userId: String = "", // Referencia a User
    val enabled: Boolean = true,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)
