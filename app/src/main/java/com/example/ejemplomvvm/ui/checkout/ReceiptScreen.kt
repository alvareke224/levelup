package com.example.ejemplomvvm.ui.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.ejemplomvvm.data.CartItem
import com.example.ejemplomvvm.data.User
import com.example.ejemplomvvm.ui.cart.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScreen(
    cartViewModel: CartViewModel,
    currentUser: User?,
    onNavigateToHome: () -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val subtotal = cartViewModel.getSubtotal()
    val discount = cartViewModel.getDiscount(currentUser)
    val total = cartViewModel.getTotalPrice(currentUser)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Compra Finalizada") }) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Compra exitosa",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("¡Gracias por tu compra!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))

                    Text("RESUMEN DEL PEDIDO", style = MaterialTheme.typography.titleMedium)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) { // Altura máxima para el scroll
                        items(cartItems) { item ->
                            ReceiptItemView(item = item)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Subtotal", style = MaterialTheme.typography.bodyLarge)
                        Text("$${subtotal.toInt()}", style = MaterialTheme.typography.bodyLarge)
                    }
                    if (discount > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Descuento Duoc (20%)", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                            Text("-$${discount.toInt()}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Divider(modifier = Modifier.padding(vertical = 16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total Pagado", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("$${total.toInt()}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = onNavigateToHome,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Volver al Inicio")
                    }
                }
            }
        }
    }
}

@Composable
fun ReceiptItemView(item: CartItem) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(item.product.imageUrl).crossfade(true).build(),
            contentDescription = "Imagen de ${item.product.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(4.dp)),
            loading = { CircularProgressIndicator(modifier = Modifier.padding(12.dp)) }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.product.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text("Cantidad: ${item.quantity}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Text("$${(item.product.price * item.quantity).toInt()}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
    }
}
