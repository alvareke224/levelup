package com.example.ejemplomvvm.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.ejemplomvvm.data.CartItem
import com.example.ejemplomvvm.data.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    currentUser: User?,
    onNavigateBack: () -> Unit,
    onNavigateToCheckout: () -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Tu carrito está vacío")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp) // Más padding horizontal
                ) {
                    items(cartItems) { cartItem ->
                        CartItemView(cartItem = cartItem, onRemove = { cartViewModel.removeFromCart(cartItem) })
                    }
                }
                SummarySection(
                    cartViewModel = cartViewModel,
                    currentUser = currentUser,
                    onCheckout = onNavigateToCheckout
                )
            }
        }
    }
}

@Composable
fun CartItemView(cartItem: CartItem, onRemove: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cartItem.product.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Imagen de ${cartItem.product.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp) 
                .clip(RoundedCornerShape(8.dp)),
            loading = { CircularProgressIndicator(modifier = Modifier.padding(20.dp)) }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = cartItem.product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(
                text = "Cantidad: ${cartItem.quantity}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = "$${(cartItem.product.price * cartItem.quantity).toInt()}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        IconButton(onClick = onRemove) {
            Icon(Icons.Filled.Delete, contentDescription = "Eliminar producto", tint = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun SummarySection(cartViewModel: CartViewModel, currentUser: User?, onCheckout: () -> Unit) {
    val subtotal = cartViewModel.getSubtotal()
    val discount = cartViewModel.getDiscount(currentUser)
    val total = cartViewModel.getTotalPrice(currentUser)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("$${total.toInt()}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onCheckout, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium) {
                Text("Proceder al Pago", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
