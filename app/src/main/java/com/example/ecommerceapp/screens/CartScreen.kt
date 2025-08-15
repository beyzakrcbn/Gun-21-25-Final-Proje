package com.example.ecommerceapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerceapp.data.CartItem
import com.example.ecommerceapp.viewmodel.MainViewModel
import java.text.NumberFormat


private fun currency(amount: Double): String =
    NumberFormat.getCurrencyInstance().format(amount)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(viewModel: MainViewModel) {
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val totalPrice = cartItems.sumOf { it.product.price * it.quantity }
    val totalItems = cartItems.sumOf { it.quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Sepet",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Sepetim",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                Badge(containerColor = MaterialTheme.colorScheme.secondary) {
                    Text(
                        text = totalItems.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (cartItems.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Default.ShoppingCartCheckout,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text("Sepetiniz boş", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    Text(
                        "Ürünleri sepete ekleyerek alışverişe başlayın",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Column(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cartItems) { cartItem ->
                        CartItemCard(
                            cartItem = cartItem,
                            onQuantityChange = { newQuantity ->
                                viewModel.updateCartItemQuantity(cartItem.product.id, newQuantity)
                            },
                            onRemoveItem = {
                                viewModel.removeFromCart(cartItem.product.id)
                            }
                        )
                    }
                }


                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(Modifier.padding(20.dp)) {
                            Text("Sipariş Özeti", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(12.dp))


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Ürün Sayısı:")
                                Text("$totalItems adet")
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Ara Toplam:")
                                Text(currency(totalPrice))
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Kargo:")
                                Text("Ücretsiz", color = MaterialTheme.colorScheme.primary)
                            }

                            HorizontalDivider(Modifier.padding(vertical = 8.dp)) // Divider yerine HorizontalDivider

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Toplam:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    currency(totalPrice),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Spacer(Modifier.height(16.dp))

                            Button(
                                onClick = { /* ödeme akışı */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Icon(Icons.Default.Payment, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("ÖDEMEYİ TAMAMLA", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}


