package com.example.ejemplomvvm.ui.auth

import androidx.lifecycle.ViewModel
import com.example.ejemplomvvm.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    // Usa directamente el objeto Singleton
    private val repository = UserRepository

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun register(email: String, password: String, birthDate: String): Boolean {
        val errorMessage = repository.register(email, password, birthDate)
        _error.value = errorMessage
        return errorMessage == null // Devuelve true si el registro fue exitoso
    }

    fun login(email: String, password: String): Boolean {
        val errorMessage = repository.login(email, password)
        _error.value = errorMessage
        return errorMessage == null // Devuelve true si el inicio de sesión fue exitoso
    }

    fun clearError() {
        _error.value = null
    }
}
