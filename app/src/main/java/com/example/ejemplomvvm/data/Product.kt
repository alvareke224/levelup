package com.example.ejemplomvvm.data

data class Product(
    val code: String,
    val category: Category,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String,
    val ratings: MutableList<Int> = mutableListOf() // Lista para guardar las puntuaciones (de 1 a 5)
)
