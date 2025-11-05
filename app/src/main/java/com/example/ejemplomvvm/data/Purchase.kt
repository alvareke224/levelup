package com.example.ejemplomvvm.data

import java.util.Date

data class Purchase(
    val items: List<CartItem>,
    val totalPrice: Double,
    val purchaseDate: Date = Date() // Se asigna la fecha actual por defecto
)
