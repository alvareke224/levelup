package com.example.ejemplomvvm.data

import java.util.Date

data class User(
    val email: String,
    val password: String, // En una app real, esto debería ser un hash
    val birthDate: Date,
    val hasDuocDiscount: Boolean = false,
    var nickname: String? = null,
    var profileImageUrl: String? = null,
    val purchaseHistory: MutableList<Purchase> = mutableListOf()
)
