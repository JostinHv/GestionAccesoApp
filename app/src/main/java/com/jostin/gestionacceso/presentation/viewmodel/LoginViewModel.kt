package com.jostin.gestionacceso.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jostin.gestionacceso.data.repository.UserRepository
import com.jostin.gestionacceso.utils.Resource

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository()

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun onLoginClick(username: String, password: String) {
        println("Credenciales: $username $password")
        CoroutineScope(Dispatchers.IO).launch {
            val result = userRepository.authenticateUser(username, password)
            CoroutineScope(Dispatchers.Main).launch {
                when (result) {
                    is Resource.Success -> {
                        if (result.data.role == "admin") {
                            _loginResult.value = LoginResult.Admin
                        } else if (result.data.role == "docente") {
                            println("Es docente")
                            _loginResult.value = LoginResult.Teacher
                        }
                    }
                    is Resource.Error -> {
                        _loginResult.value = LoginResult.Error(result.message ?: "Error desconocido")
                    }
                }
            }
        }
    }
}

sealed class LoginResult {
    data object Admin : LoginResult()
    data object Teacher : LoginResult()
    data class Error(val message: String) : LoginResult()
}
