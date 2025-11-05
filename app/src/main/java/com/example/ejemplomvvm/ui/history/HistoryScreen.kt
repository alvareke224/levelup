package com.example.ejemplomvvm.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ejemplomvvm.data.Purchase
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val purchaseHistory by historyViewModel.purchaseHistory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Compras") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (purchaseHistory.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No tienes compras en tu historial.")
            }
        } else {
            LazyColumn(contentPadding = padding, modifier = Modifier.padding(horizontal = 16.dp)) {
                items(purchaseHistory) { purchase ->
                    PurchaseCard(purchase = purchase, onRate = { productCode, rating -> historyViewModel.addRating(productCode, rating) })
                }
            }
        }
    }
}

@Composable
fun PurchaseCard(purchase: Purchase, onRate: (String, Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            Text(
                text = "Compra del ${sdf.format(purchase.purchaseDate)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            purchase.items.forEach {
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("${it.quantity} x ${it.product.name}")
                        Text("$${(it.product.price * it.quantity).toInt()}")
                    }
                    RatingBar(currentRating = 0, onRatingChanged = { rating -> onRate(it.product.code, rating) })
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total", fontWeight = FontWeight.Bold)
                Text("$${purchase.totalPrice.toInt()}", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RatingBar(currentRating: Int, onRatingChanged: (Int) -> Unit) {
    Row {
        (1..5).forEach { star ->
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Estrella $star",
                tint = if (star <= currentRating) Color.Yellow else Color.Gray,
                modifier = Modifier.clickable { onRatingChanged(star) }
            )
        }
    }
}
