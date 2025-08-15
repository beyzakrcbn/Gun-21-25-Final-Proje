package com.example.ecommerceapp.data

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int,
    @SerializedName("title") val name: String,
    val price: Double,
    val description: String,
    @SerializedName("thumbnail") val imageUrl: String = "",
    val category: String = "",
    val rating: Float = 0f,
    val stock: Int = 0,
    val reviews: Int = 0 // API'de reviews yok, istersen sabit tutarsın
)

