package com.jostin.gestionacceso.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jostin.gestionacceso.domain.models.Access
import java.util.Calendar

class HistorialViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _accessList = MutableLiveData<List<Access>>()
    val accessList: LiveData<List<Access>> get() = _accessList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        // fetchAccessList()
        cargarAccesos()
    }


    private fun cargarAccesos() {
        // Aquí normalmente cargarías los datos de una fuente de datos real
        // Por ahora, usaremos datos de ejemplo
        _accessList.value = listOf(
            Access(
                id = "Lab 1",
                userName = "Johan Lopez",
                labName = "Laboratorio 1",
                courseName = "Programación I",
                cardId = "",
                entryDay = Timestamp(Calendar.getInstance().apply { set(2023, 4, 15, 0, 0) }.time),
                entryTime = Timestamp(Calendar.getInstance().apply { set(2023, 4, 15, 8, 0) }.time),
                exitTime = Timestamp(Calendar.getInstance().apply { set(2023, 4, 15, 10, 0) }.time)
            ),
            Access(
                id = "Lab 2",
                userName = "Hugo Caselli",
                labName = "Laboratorio 1",
                courseName = "Bases de Datos",
                cardId = "",
                entryDay = Timestamp(Calendar.getInstance().apply { set(2023, 4, 16, 0, 0) }.time),
                entryTime = Timestamp(
                    Calendar.getInstance().apply { set(2023, 4, 16, 10, 30) }.time
                ),
                exitTime = Timestamp(Calendar.getInstance().apply { set(2023, 4, 16, 12, 30) }.time)
            ),
            Access(
                id = "Lab 3",
                userName = "Guillermo Gil",
                labName = "Laboratorio 1",
                courseName = "Redes",
                cardId = "",
                entryDay = Timestamp(Calendar.getInstance().apply { set(2023, 4, 17, 0, 0) }.time),
                entryTime = Timestamp(
                    Calendar.getInstance().apply { set(2023, 4, 17, 14, 0) }.time
                ),
                exitTime = Timestamp(Calendar.getInstance().apply { set(2023, 4, 17, 16, 0) }.time)
            )
        )
    }

    private fun fetchAccessList() {
        _loading.value = true
        db.collection("access")
            .orderBy("entryTime", Query.Direction.DESCENDING) // Ordena por hora de entrada
            .get()
            .addOnSuccessListener { result ->
                val accessItems = result.map { document ->
                    document.toObject(Access::class.java).copy(id = document.id)
                }
                _accessList.value = accessItems
                _loading.value = false
            }
            .addOnFailureListener { exception ->
                _error.value = "Error al obtener datos: ${exception.message}"
                _loading.value = false
            }
    }}