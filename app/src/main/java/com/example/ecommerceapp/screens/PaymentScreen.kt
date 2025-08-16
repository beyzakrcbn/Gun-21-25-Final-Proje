package com.example.ecommerceapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ecommerceapp.data.CardInfo
import com.example.ecommerceapp.data.PaymentData
import com.example.ecommerceapp.data.PaymentStatus
import com.example.ecommerceapp.viewmodel.MainViewModel
import java.text.NumberFormat

private fun currency(amount: Double): String =
    NumberFormat.getCurrencyInstance().format(amount)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val paymentStatus by viewModel.paymentStatus.collectAsStateWithLifecycle()
    val totalAmount = cartItems.sumOf { it.product.price * it.quantity }

    // Form state'leri
    var selectedPaymentMethod by remember { mutableStateOf("credit_card") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }

    // Payment status'u dinle
    LaunchedEffect(paymentStatus) {
        when (paymentStatus) {
            is PaymentStatus.Success -> {
                // Başarılı ödeme sonrası ana sayfaya dön
                navController.navigate("home") {
                    popUpTo("cart") { inclusive = true }
                }
                viewModel.resetPaymentStatus()
            }
            else -> { /* Do nothing */ }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Header
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
                IconButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Geri",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Ödeme",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = currency(totalAmount),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Payment Methods
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Ödeme Yöntemi",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FilterChip(
                            onClick = { selectedPaymentMethod = "credit_card" },
                            label = { Text("💳 Kredi Kartı") },
                            selected = selectedPaymentMethod == "credit_card",
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            onClick = { selectedPaymentMethod = "cash_on_delivery" },
                            label = { Text("💵 Kapıda Ödeme") },
                            selected = selectedPaymentMethod == "cash_on_delivery",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Credit Card Form
            if (selectedPaymentMethod == "credit_card") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Kart Bilgileri",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        OutlinedTextField(
                            value = cardNumber,
                            onValueChange = {
                                // Format card number
                                val cleaned = it.replace(" ", "")
                                if (cleaned.length <= 16) {
                                    val formatted = cleaned.chunked(4).joinToString(" ")
                                    cardNumber = formatted
                                }
                            },
                            label = { Text("Kart Numarası") },
                            placeholder = { Text("1234 5678 9012 3456") },
                            leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedTextField(
                                value = expiryDate,
                                onValueChange = {
                                    val cleaned = it.replace("/", "")
                                    if (cleaned.length <= 4) {
                                        val formatted = when {
                                            cleaned.length >= 3 -> "${cleaned.substring(0, 2)}/${cleaned.substring(2)}"
                                            else -> cleaned
                                        }
                                        expiryDate = formatted
                                    }
                                },
                                label = { Text("MM/YY") },
                                placeholder = { Text("12/25") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )

                            OutlinedTextField(
                                value = cvv,
                                onValueChange = { if (it.length <= 3) cvv = it },
                                label = { Text("CVV") },
                                placeholder = { Text("123") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = cardHolderName,
                            onValueChange = { cardHolderName = it },
                            label = { Text("Kart Sahibi Adı") },
                            placeholder = { Text("Adınız Soyadınız") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Shipping Address
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Teslimat Adresi",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Adres") },
                        placeholder = { Text("Tam adresinizi girin") },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                        minLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = city,
                            onValueChange = { city = it },
                            label = { Text("Şehir") },
                            placeholder = { Text("İstanbul") },
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedTextField(
                            value = postalCode,
                            onValueChange = { postalCode = it },
                            label = { Text("Posta Kodu") },
                            placeholder = { Text("34000") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Order Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Sipariş Özeti",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    cartItems.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "${item.product.name} (${item.quantity}x)",
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                currency(item.product.price * item.quantity),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Toplam",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            currency(totalAmount),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Payment Button
            Button(
                onClick = {
                    val paymentData = PaymentData(
                        cartItems = cartItems,
                        totalAmount = totalAmount,
                        paymentMethod = selectedPaymentMethod,
                        cardInfo = if (selectedPaymentMethod == "credit_card") {
                            CardInfo(
                                cardNumber = cardNumber.replace(" ", ""),
                                expiryDate = expiryDate,
                                cvv = cvv,
                                cardHolderName = cardHolderName
                            )
                        } else null
                    )
                    viewModel.processPayment(paymentData)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = paymentStatus !is PaymentStatus.Loading &&
                        cartItems.isNotEmpty() &&
                        address.isNotBlank() &&
                        city.isNotBlank() &&
                        postalCode.isNotBlank() &&
                        (selectedPaymentMethod == "cash_on_delivery" ||
                                (cardNumber.replace(" ", "").length == 16 &&
                                        expiryDate.length == 5 &&
                                        cvv.length == 3 &&
                                        cardHolderName.isNotBlank()))
            ) {
                if (paymentStatus is PaymentStatus.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("İşleniyor...")
                } else {
                    Icon(Icons.Default.Payment, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "${currency(totalAmount)} ÖDE",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Error message
            (paymentStatus as? PaymentStatus.Error)?.let { errorStatus ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            errorStatus.message, // EN GÜVENLİ KULLANIM
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}