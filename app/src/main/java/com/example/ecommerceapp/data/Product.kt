package com.example.ecommerceapp.data

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String = "",
    val category: String = "",
    val rating: Float = 0f,
    val stock: Int = 0
)
