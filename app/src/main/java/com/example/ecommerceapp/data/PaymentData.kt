package com.example.ecommerceapp.data


data class PaymentData(
    val cartItems: List<CartItem>,
    val totalAmount: Double,
    val paymentMethod: String,
    val cardInfo: CardInfo? = null
)
