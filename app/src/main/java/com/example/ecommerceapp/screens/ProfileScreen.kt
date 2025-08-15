package com.example.ecommerceapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController // NavController'ı almayı unutmayın

@Composable
fun ProfileScreen(navController: NavController) { // NavController parametresini ekledik
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bu sizin Profil Ekranınız!")
        // Buraya kullanıcı bilgileri, ayarlar vb. eklenebilir
    }
}
