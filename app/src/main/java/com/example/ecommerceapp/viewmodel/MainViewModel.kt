package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.ecommerceapp.data.Product
import com.ecommerceapp.data.CartItem
import com.ecommerceapp.repository.ProductRepository


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

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.product.id == product.id }

        if (existingItem != null) {
            val index = currentItems.indexOf(existingItem)
            currentItems[index] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            currentItems.add(CartItem(product, 1))
        }

        _cartItems.value = currentItems
    }
}