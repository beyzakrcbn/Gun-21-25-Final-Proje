package com.example.ecommerceapp.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val image: String,
    @SerializedName("token") val token: String // API'den gelen kimlik doğrulama token'ı
)

