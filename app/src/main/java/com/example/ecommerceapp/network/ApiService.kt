package com.example.ecommerceapp.network

import com.example.ecommerceapp.data.ProductResponse
import com.example.ecommerceapp.data.User
import com.example.ecommerceapp.data.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<ProductResponse>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<ProductResponse>

    @POST("auth/login") // dummyjson.com'un login endpoint'i
    suspend fun loginUser(@Body request: User): Response<LoginResponse> // UserRequest yerine User, LoginResponse kullanıldı
}