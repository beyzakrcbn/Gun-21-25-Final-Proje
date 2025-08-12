package com.example.ecommerceapp.data

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val rating: Float = 0f,
    val reviews: Int = 0
)

data class User(
    val email: String,
    val password: String
)

data class CartItem(
    val product: Product,
    val quantity: Int
)
