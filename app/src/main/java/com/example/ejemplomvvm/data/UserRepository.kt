package com.example.ejemplomvvm.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

object UserRepository {
    private val users = mutableListOf<User>()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun register(email: String, password: String, birthDate: String): String? {
        if (users.any { it.email.equals(email, ignoreCase = true) }) {
            return "El correo electrónico ya está en uso."
        }

        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            val parsedDate = sdf.parse(birthDate) ?: return "El formato de fecha es inválido."

            val eighteenYearsAgo = Calendar.getInstance().apply {
                add(Calendar.YEAR, -18)
            }.time

            if (parsedDate.after(eighteenYearsAgo)) {
                return "Debes ser mayor de 18 años para registrarte."
            }

            val hasDuocDiscount = email.endsWith("@duoc.cl", ignoreCase = true) ||
                                  email.endsWith("@profesor.duoc.cl", ignoreCase = true) ||
                                  email.endsWith("@duocuc.cl", ignoreCase = true)
            val newUser = User(email, password, parsedDate, hasDuocDiscount)
            users.add(newUser)
            _currentUser.value = newUser // Inicia sesión automáticamente
            return null // Éxito
        } catch (e: Exception) {
            return "Formato de fecha inválido. Usa DD/MM/AAAA."
        }
    }

    fun login(email: String, password: String): String? {
        val user = users.find { it.email.equals(email, ignoreCase = true) && it.password == password }
        if (user != null) {
            _currentUser.value = user
            return null // Éxito
        } else {
            return "Correo o contraseña incorrectos."
        }
    }

    fun logout() {
        _currentUser.value = null
    }

    fun updateUserProfile(email: String, nickname: String, profileImageUrl: String) {
        val user = users.find { it.email.equals(email, ignoreCase = true) }
        user?.let {
            it.nickname = nickname
            it.profileImageUrl = profileImageUrl
            _currentUser.value = it.copy() // Actualiza el estado del usuario actual
        }
    }

    fun addPurchaseToHistory(email: String, purchase: Purchase) {
        val user = users.find { it.email.equals(email, ignoreCase = true) }
        user?.purchaseHistory?.add(purchase)
        _currentUser.value = user?.copy() // Actualiza el estado para reflejar el historial
    }
}
