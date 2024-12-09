package com.jostin.gestionacceso.domain.models

import com.google.firebase.Timestamp

data class User(
    var id: String = "",
    var username : String = "",
    var password: String = "",
    var name: String = "",
    var email: String = "",
    var role: String = "Docente", // 'Docente' | 'Administrador'
    var enabled: Boolean = true,
    var createdAt: Timestamp? = null,
    var updatedAt: Timestamp? = null
)

