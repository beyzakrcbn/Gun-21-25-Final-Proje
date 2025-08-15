package com.example.ecommerceapp.repository

import com.example.ecommerceapp.data.Product

class ProductRepository {
    fun getProducts(): List<Product> = listOf(
        Product(1, "iPhone 12 Pro", 1099.0, "", "Latest iPhone with A14 Bionic chip, 5G super fast downloads and so much more.", 4.5f, 123),
        Product(2, "MacBook Pro", 1299.0, "", "MacBook Pro 13-inch with M1 chip", 4.7f, 456),
        Product(3, "AirPods Pro", 249.0, "", "Active Noise Cancellation", 4.6f, 789),
        Product(4, "iPad Air", 599.0, "", "iPad Air with M1 chip", 4.4f, 234),
        Product(5, "Apple Watch", 399.0, "", "Apple Watch Series 8", 4.3f, 567)
    )

    fun getFavorites(): List<Product> = getProducts().take(2)
}
