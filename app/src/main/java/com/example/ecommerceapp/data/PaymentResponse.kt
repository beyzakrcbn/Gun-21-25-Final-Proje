package com.example.ecommerceapp.data

data class PaymentResponse(
    val success: Boolean,
    val orderId: String,
    val message: String
)