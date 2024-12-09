package com.jostin.gestionacceso.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NfcViewModel(application: Application) : AndroidViewModel(application)  {

    private val _isClicked = MutableLiveData<Boolean>(false)
    val isClicked: LiveData<Boolean> = _isClicked

    fun toggleIsClicked() {
        _isClicked.value = _isClicked.value?.not() ?: true
    }

    // LiveData para el badge (estado de la entrada)
    private val _badgeText = MutableLiveData<String>("En curso")  // Default value
    val badgeText: LiveData<String> = _badgeText

    // LiveData para los detalles (Lab, Course, Date)
    private val _lab = MutableLiveData<String>("Lab 1")
    val lab: LiveData<String> = _lab

    private val _course = MutableLiveData<String>("Programación I")
    val course: LiveData<String> = _course

    private val _date = MutableLiveData<String>("2024-12-08")
    val date: LiveData<String> = _date

    private val _entradaTime = MutableLiveData<String>("Entrada: -")
    val entradaTime: LiveData<String> = _entradaTime

    private val _salidaTime = MutableLiveData<String>("Salida: -")
    val salidaTime: LiveData<String> = _salidaTime

    // Métodos para actualizar la hora de entrada y salida
    fun updateEntradaTime(time: String) {
        _entradaTime.value = "Entrada: $time"
        _date.value = getCurrentDate()
    }

    fun updateSalidaTime(time: String) {
        _salidaTime.value = "Salida: $time"
    }

    // Método para obtener la fecha actual
    private fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(currentDate)  // Devuelve la fecha en formato "yyyy-MM-dd"
    }

    // Método para alternar el estado del badge
    fun toggleBadgeState(isClicked: Boolean) {
        // Si el botón ya fue presionado (isClicked = true), se marca como "Completado"
        if (isClicked) {
            _badgeText.value = "Completado"
        } else {
            _badgeText.value = "En curso"
        }
    }


}
