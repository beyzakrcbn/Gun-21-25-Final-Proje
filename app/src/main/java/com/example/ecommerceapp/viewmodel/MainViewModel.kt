package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.CartItem
import com.example.ecommerceapp.data.Product
import com.example.ecommerceapp.data.ThemePreferences
import com.example.ecommerceapp.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val themePreferences: ThemePreferences,
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    // ---------- Tema ----------
    val isDarkTheme: StateFlow<Boolean> =
        themePreferences.isDarkTheme.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleTheme() {
        viewModelScope.launch {
            themePreferences.setDarkTheme(!isDarkTheme.value)
        }
    }

    // ---------- Products ----------
    private val _products = MutableStateFlow(emptyList<Product>())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    // ---------- Cart ----------
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // ---------- Favorites ----------
    private val _favorites = MutableStateFlow(emptyList<Product>())
    val favorites: StateFlow<List<Product>> = _favorites.asStateFlow()

    private val _favoriteProductIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteProductIds: StateFlow<Set<Int>> = _favoriteProductIds.asStateFlow()

    // ---------- Search ----------
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

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

    fun removeFromCart(productId: Int) {
        _cartItems.value = _cartItems.value.filter { it.product.id != productId }
    }

    fun updateCartItemQuantity(productId: Int, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(productId); return
        }
        val currentCart = _cartItems.value.toMutableList()
        val i = currentCart.indexOfFirst { it.product.id == productId }
        if (i != -1) {
            currentCart[i] = currentCart[i].copy(quantity = newQuantity)
            _cartItems.value = currentCart
        }
    }

    fun toggleFavorite(productId: Int) {
        val set = _favoriteProductIds.value.toMutableSet()
        if (!set.add(productId)) set.remove(productId)
        _favoriteProductIds.value = set
    }

    fun isFavorite(productId: Int): Boolean =
        _favoriteProductIds.value.contains(productId)

    fun getFavoriteProducts(): List<Product> =
        _products.value.filter { _favoriteProductIds.value.contains(it.id) }

    fun getProductById(productId: Int): Product? =
        _products.value.find { it.id == productId }

    fun updateSearchQuery(query: String) { _searchQuery.value = query }

    fun getFilteredProducts(): List<Product> {
        val q = _searchQuery.value
        return if (q.isBlank()) _products.value else _products.value.filter {
            it.name.contains(q, true) ||
                    it.description.contains(q, true) ||
                    it.category.contains(q, true)
        }
    }
}
