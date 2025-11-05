package com.example.ejemplomvvm.ui

import androidx.lifecycle.ViewModel
import com.example.ejemplomvvm.data.User
import com.example.ejemplomvvm.data.UserRepository
import kotlinx.coroutines.flow.StateFlow

class SessionViewModel : ViewModel() {
    private val repository = UserRepository

    val currentUser: StateFlow<User?> = repository.currentUser

    fun logout() {
        repository.logout()
    }
}
