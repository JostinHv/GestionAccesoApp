package com.jostin.gestionacceso.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.gestionacceso.domain.models.User
import com.jostin.gestionacceso.utils.Resource
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val userCollection = firestore.collection("users")

    // Método para autenticar al usuario con email y contraseña
    suspend fun authenticateUser(username: String, password: String): Resource<User> {
        return try {
            // Suponiendo que la colección de usuarios se llama "users"
            val userDoc = firestore.collection("users")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get()
                .await()

            if (userDoc.isEmpty) {
                println("Credenciales incorrectas")
                Resource.Error("Credenciales incorrectas")
            } else {
                val user = userDoc.documents.first().toObject(User::class.java)
                println("Usuario autenticado: $user")
                Resource.Success(user!!)
            }
        } catch (e: Exception) {
            Resource.Error("Error en la autenticación: ${e.message}")
        }
    }

    // Crear usuario
    fun createUser(user: User, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        userCollection.add(user)
            .addOnSuccessListener { documentReference ->
                onSuccess("Usuario creado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { exception ->
                onFailure("Error al crear usuario: ${exception.message}")
            }
    }

    // Obtener todos los usuarios
    fun getAllUsers(onSuccess: (List<User>) -> Unit, onFailure: (String) -> Unit) {
        userCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val users = querySnapshot.toObjects(User::class.java)
                onSuccess(users)
            }
            .addOnFailureListener { exception ->
                onFailure("Error al obtener usuarios: ${exception.message}")
            }
    }

    // Actualizar usuario
    fun updateUser(user: User, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        userCollection.document(user.id).set(user)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure("Error al actualizar usuario: ${exception.message}")
            }
    }

    // Eliminar usuario
    fun deleteUser(userId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        userCollection.document(userId).delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure("Error al eliminar usuario: ${exception.message}")
            }
    }
}
