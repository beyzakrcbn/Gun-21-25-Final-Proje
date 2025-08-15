package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.CartItem
import com.example.ecommerceapp.data.Product
import com.example.ecommerceapp.data.ThemePreferences
import com.example.ecommerceapp.data.User
import com.example.ecommerceapp.data.LoginResponse
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

    // Tema
    val isDarkTheme: StateFlow<Boolean> =
        themePreferences.isDarkTheme.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleTheme() {
        viewModelScope.launch {
            themePreferences.setDarkTheme(!isDarkTheme.value)
        }
    }

    // Products
    private val _products = MutableStateFlow(emptyList<Product>())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    // Cart
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Favorites
    private val _favoriteProductIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteProductIds: StateFlow<Set<Int>> = _favoriteProductIds.asStateFlow()

    // Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Login State
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    private val _currentUser = MutableStateFlow<LoginResponse?>(null)
    val currentUser: StateFlow<LoginResponse?> = _currentUser.asStateFlow()

    init {
        loadData()
    }

    // Login
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginError.value = null
            try {
                val request = User(username = username, password = password)
                val response = repository.loginUser(request)

                if (response.isSuccessful && response.body() != null) {
                    _loginSuccess.value = true
                    _currentUser.value = response.body()
                    println("✅ Login Successful: ${response.body()?.username}")
                } else {
                    _loginSuccess.value = false
                    _loginError.value = "Giriş başarısız: " + (response.errorBody()?.string() ?: "Bilinmeyen hata")
                    println("❌ Login Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _loginSuccess.value = false
                _loginError.value = "Ağ hatası: " + (e.localizedMessage ?: "Bilinmeyen hata")
                println("⚠ Login Exception: ${e.localizedMessage}")
            }
        }
    }

    fun logout() {
        _loginSuccess.value = false
        _currentUser.value = null
    }

    // Ürünleri yükle
    private fun loadData() {
        viewModelScope.launch {
            try {
                val response = repository.fetchProductsFromApi()
                if (response.isSuccessful) {
                    _products.value = response.body()?.products ?: repository.getProducts()
                } else {
                    _products.value = repository.getProducts()
                }
            } catch (e: Exception) {
                _products.value = repository.getProducts()
            }
        }
    }

    // Search
    fun searchProducts(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            loadData()
            return
        }

        viewModelScope.launch {
            try {
                val response = repository.searchProducts(query)
                if (response.isSuccessful) {
                    _products.value = response.body()?.products ?: emptyList()
                }
            } catch (e: Exception) {
                println("Search Error: ${e.localizedMessage}")
            }
        }
    }

    // Cart
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

    // Favorites
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
}
