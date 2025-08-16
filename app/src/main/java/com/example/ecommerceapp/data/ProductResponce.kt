
package com.example.ecommerceapp.data

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
    // API yanıtınızdaki varsa diğer alanlar (örneğin: total, skip, limit)
)