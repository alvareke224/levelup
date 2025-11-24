package com.example.ejemplomvvm.data

import com.example.ejemplomvvm.data.network.GistApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ProductRepository {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    suspend fun fetchProductsFromGist() {
        try {
            val productList = GistApi.retrofitService.getProducts()
            _products.value = productList
        } catch (e: Exception) {
            // Handle error
        }
    }

    fun addRatingToProduct(productCode: String, rating: Int) {
        val currentProducts = _products.value.toMutableList()
        val productIndex = currentProducts.indexOfFirst { it.code == productCode }
        if (productIndex != -1) {
            val product = currentProducts[productIndex]
            product.ratings.add(rating)
            _products.value = currentProducts.toList() // Emite una nueva lista para la actualización
        }
    }
}