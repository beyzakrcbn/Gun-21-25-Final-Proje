package com.example.ecommerceapp.repository

import com.example.ecommerceapp.data.Product

class ProductRepository {

    fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                name = "iPhone 12 Pro",
                price = 999.0,
                description = "Latest iPhone with A14 Bionic chip, 5G support, and amazing camera system.",
                category = "Electronics",
                rating = 4.5f,
                stock = 25
            ),
            Product(
                id = 2,
                name = "MacBook Pro",
                price = 1199.0,
                description = "MacBook Pro 13-inch with M1 chip, 8GB unified memory, and 256GB SSD storage.",
                category = "Electronics",
                rating = 4.7f,
                stock = 15
            ),
            Product(
                id = 3,
                name = "AirPods Pro",
                price = 249.0,
                description = "Active Noise Cancellation, Transparency mode, and spatial audio.",
                category = "Electronics",
                rating = 4.6f,
                stock = 50
            ),
            Product(
                id = 4,
                name = "Apple Watch",
                price = 399.0,
                description = "Apple Watch Series 7 with larger Always-On Retina display.",
                category = "Electronics",
                rating = 4.4f,
                stock = 30
            ),
            Product(
                id = 5,
                name = "iPad Air",
                price = 599.0,
                description = "iPad Air with 10.9-inch Liquid Retina display and A14 Bionic chip.",
                category = "Electronics",
                rating = 4.5f,
                stock = 20
            )
        )
    }

    fun getFavorites(): List<Product> {
        return emptyList()
    }

    fun getProductById(id: Int): Product? {
        return getProducts().find { it.id == id }
    }
}
