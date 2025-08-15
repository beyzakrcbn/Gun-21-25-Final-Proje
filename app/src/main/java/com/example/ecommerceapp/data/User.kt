package com.example.ecommerceapp.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username") val username: String, // DummyJSON login için username olmalı
    @SerializedName("password") val password: String
)