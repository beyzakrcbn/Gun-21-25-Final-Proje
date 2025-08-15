package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.data.CartItem
import com.example.ecommerceapp.data.Product
import com.example.ecommerceapp.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



class MainViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow(emptyList<Product>())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _favorites = MutableStateFlow(emptyList<Product>())
    val favorites: StateFlow<List<Product>> = _favorites.asStateFlow()

    private val _cartItems = MutableStateFlow(emptyList<CartItem>())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()


    init {
        loadData()
    }

    private fun loadData() {
        _products.value = repository.getProducts()
        _favorites.value = repository.getFavorites()
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.product.id == product.id }
        if (index >= 0) {
            val old = current[index]
            current[index] = old.copy(quantity = old.quantity + quantity)
        } else {
            current += CartItem(product, quantity)
        }
        _cartItems.value = current
    }

    fun updateCartItemQuantity(productId: Int, newQuantity: Int) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.product.id == productId }
        if (index >= 0) {
            if (newQuantity <= 0) {
                current.removeAt(index)
            } else {
                current[index] = current[index].copy(quantity = newQuantity)
            }
            _cartItems.value = current
        }
    }

    fun removeFromCart(productId: Int) {
        val current = _cartItems.value.filterNot { it.product.id == productId }
        _cartItems.value = current
    }
}



