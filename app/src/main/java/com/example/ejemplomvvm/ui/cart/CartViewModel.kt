package com.example.ejemplomvvm.ui.cart

import androidx.lifecycle.ViewModel
import com.example.ejemplomvvm.data.CartItem
import com.example.ejemplomvvm.data.Product
import com.example.ejemplomvvm.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product, quantity: Int) {
        val currentList = _cartItems.value.toMutableList()
        val existingItem = currentList.find { it.product.code == product.code }

        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            currentList.add(CartItem(product, quantity))
        }
        _cartItems.value = currentList
    }

    fun removeFromCart(cartItem: CartItem) {
        _cartItems.value = _cartItems.value - cartItem
    }

    fun getSubtotal(): Double {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }

    fun getDiscount(currentUser: User?): Double {
        return if (currentUser?.hasDuocDiscount == true) getSubtotal() * 0.20 else 0.0
    }

    fun getTotalPrice(currentUser: User?): Double {
        val subtotal = getSubtotal()
        val discount = getDiscount(currentUser)
        return subtotal - discount
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}
