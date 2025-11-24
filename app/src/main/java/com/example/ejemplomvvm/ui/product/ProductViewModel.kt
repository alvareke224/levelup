package com.example.ejemplomvvm.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemplomvvm.data.Category
import com.example.ejemplomvvm.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    // Ahora se suscribe al StateFlow del repositorio
    private val _allProducts = ProductRepository.products

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    init {
        viewModelScope.launch {
            ProductRepository.fetchProductsFromGist()
        }
    }

    val filteredProducts = combine(_searchText, _selectedCategory, _allProducts) { text, category, products ->
        val textFiltered = if (text.isBlank()) {
            products
        } else {
            products.filter {
                it.name.contains(text, ignoreCase = true)
            }
        }

        if (category == null) {
            textFiltered
        } else {
            textFiltered.filter { it.category == category }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _allProducts.value
    )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onCategoryChange(category: Category?) {
        _selectedCategory.value = category
    }
}
