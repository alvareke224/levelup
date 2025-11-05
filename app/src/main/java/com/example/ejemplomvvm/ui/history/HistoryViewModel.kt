package com.example.ejemplomvvm.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemplomvvm.data.ProductRepository
import com.example.ejemplomvvm.data.Purchase
import com.example.ejemplomvvm.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    private val repository = UserRepository

    private val _purchaseHistory = MutableStateFlow<List<Purchase>>(emptyList())
    val purchaseHistory = _purchaseHistory.asStateFlow()

    init {
        viewModelScope.launch {
            repository.currentUser.collect { user ->
                _purchaseHistory.value = user?.purchaseHistory ?: emptyList()
            }
        }
    }

    fun addRating(productCode: String, rating: Int) {
        ProductRepository.addRatingToProduct(productCode, rating)
    }
}
