package com.example.ecommerceapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommerceapp.viewmodel.MainViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val currentUser = viewModel.currentUser.collectAsState().value
    val isDarkTheme = viewModel.isDarkTheme.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profil",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Kullanıcı bilgileri
        if (currentUser != null) {
            Text(text = "👤 Kullanıcı Adı: ${currentUser.username}")
            Text(text = "📧 Email: ${currentUser.email}")
        } else {
            Text("Kullanıcı bilgisi bulunamadı")
        }


        Spacer(modifier = Modifier.height(32.dp))

        // Tema Switch
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Karanlık Tema")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { viewModel.toggleTheme() }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Logout Button
        Button(
            onClick = {
                viewModel.logout()
                navController.navigate("login") {
                    popUpTo("profile") { inclusive = true } // geri tuşuna basınca profil sayfasına dönmesin
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Çıkış Yap", color = MaterialTheme.colorScheme.onError)
        }
    }
}
