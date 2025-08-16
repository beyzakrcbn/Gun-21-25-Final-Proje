package com.example.ecommerceapp.viewmodel

import android.util.Log
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
                    Log.d("LOGIN", "Login Successful: ${response.body()?.username}")
                } else {
                    _loginSuccess.value = false
                    _loginError.value = "Giriş başarısız: " + (response.errorBody()?.string() ?: "Bilinmeyen hata")
                    Log.e("LOGIN", "Login Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _loginSuccess.value = false
                _loginError.value = "Ağ hatası: " + (e.localizedMessage ?: "Bilinmeyen hata")
                Log.e("LOGIN", "Login Exception: ${e.localizedMessage}")
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
                    val products = response.body()?.products ?: emptyList()
                    _products.value = products
                    Log.d("API", "Ürünler başarıyla yüklendi: ${products.size} adet")

                    // İlk ürünün bilgilerini logla
                    if (products.isNotEmpty()) {
                        val firstProduct = products.first()
                        Log.d("API_TEST", "Thumbnail: ${firstProduct.imageUrl}")
                        Log.d("API_TEST", "Images: ${firstProduct.images}")
                    }
                } else {
                    _products.value = repository.getProducts()
                    Log.e("API", "API isteği başarısız: ${response.code()}")
                }
            } catch (e: Exception) {
                _products.value = repository.getProducts()
                Log.e("API", "Ürün yükleme hatası: ${e.localizedMessage}")
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
                    val products = response.body()?.products ?: emptyList()
                    _products.value = products
                    Log.d("SEARCH", "${products.size} ürün bulundu")
                } else {
                    Log.e("SEARCH", "Arama başarısız: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("SEARCH", "Arama hatası: ${e.localizedMessage}")
            }
        }
    }

    // Cart - GÜNCELLENDİ
    fun addToCart(product: Product, quantity: Int = 1) {
        val current = _cartItems.value.toMutableList()
        val existingItemIndex = current.indexOfFirst { it.product.id == product.id }

        if (existingItemIndex >= 0) {
            // Ürün sepette varsa miktarı güncelle
            val existingItem = current[existingItemIndex]
            current[existingItemIndex] = existingItem.copy(quantity = existingItem.quantity + quantity)
            Log.d("CART", "${product.name} ürününün miktarı güncellendi: ${existingItem.quantity + quantity}")
        } else {
            // Yeni ürün ekle
            current.add(CartItem(product, quantity))
            Log.d("CART", "Yeni ürün eklendi: ${product.name} (Adet: $quantity)")
        }

        _cartItems.value = current
        Log.d("CART", "Sepetteki toplam ürün sayısı: ${_cartItems.value.size}")
    }

    fun removeFromCart(productId: Int) {
        _cartItems.value = _cartItems.value.filter { it.product.id != productId }
        Log.d("CART", "Ürün sepetten çıkarıldı: $productId")
    }

    fun updateCartItemQuantity(productId: Int, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(productId)
            return
        }

        _cartItems.value = _cartItems.value.map { item ->
            if (item.product.id == productId) {
                Log.d("CART", "${item.product.name} miktarı güncellendi: ${item.quantity} -> $newQuantity")
                item.copy(quantity = newQuantity)
            } else {
                item
            }
        }
    }

    // Favorites - GÜNCELLENDİ
    fun toggleFavorite(productId: Int) {
        _favoriteProductIds.value = _favoriteProductIds.value.toMutableSet().apply {
            if (contains(productId)) {
                remove(productId)
                Log.d("FAVORITE", "Ürün favorilerden çıkarıldı: $productId")
            } else {
                add(productId)
                Log.d("FAVORITE", "Ürün favorilere eklendi: $productId")
            }
        }
        Log.d("FAVORITE", "Güncel favori sayısı: ${_favoriteProductIds.value.size}")
    }

    fun isFavorite(productId: Int): Boolean {
        return _favoriteProductIds.value.contains(productId).also {
            Log.d("FAVORITE", "$productId favori mi: $it")
        }
    }
}