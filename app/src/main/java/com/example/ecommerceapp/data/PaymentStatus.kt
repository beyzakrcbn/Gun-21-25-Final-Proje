package com.example.ecommerceapp.data

sealed class PaymentStatus {
    object Idle : PaymentStatus()
    object Loading : PaymentStatus()
    data class Success(val orderId: String) : PaymentStatus()
    data class Error(val message: String) : PaymentStatus()
}