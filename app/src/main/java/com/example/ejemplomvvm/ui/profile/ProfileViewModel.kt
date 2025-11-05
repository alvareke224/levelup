package com.example.ejemplomvvm.ui.profile

import androidx.lifecycle.ViewModel
import com.example.ejemplomvvm.data.UserRepository

class ProfileViewModel : ViewModel() {
    private val repository = UserRepository

    fun updateUserProfile(email: String, nickname: String, profileImageUrl: String) {
        repository.updateUserProfile(email, nickname, profileImageUrl)
    }
}
