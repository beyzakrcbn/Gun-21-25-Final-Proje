
package com.example.ecommerceapp.data

data class ProductResponse(
    val products: List<Product>,
    // API yanıtınızdaki varsa diğer alanlar (örneğin: total, skip, limit)
)