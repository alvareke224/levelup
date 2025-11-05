package com.example.ejemplomvvm.ui.product

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.ejemplomvvm.data.Category
import com.example.ejemplomvvm.data.Product
import com.example.ejemplomvvm.data.User
import com.example.ejemplomvvm.ui.cart.CartViewModel
import com.example.ejemplomvvm.ui.history.RatingBar
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel,
    currentUser: User?,
    onNavigateToCart: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val searchText by productViewModel.searchText.collectAsState()
    val selectedCategory by productViewModel.selectedCategory.collectAsState()
    val filteredProducts by productViewModel.filteredProducts.collectAsState()
    val context = LocalContext.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Level-Up Gamer") },
                actions = {
                    if (currentUser != null) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onNavigateToProfile() }) {
                            Text(
                                text = currentUser.nickname ?: currentUser.email,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(end = 8.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            SubcomposeAsyncImage(
                                model = currentUser.profileImageUrl,
                                contentDescription = "Foto de perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(40.dp).clip(CircleShape),
                                loading = { CircularProgressIndicator() },
                                error = { Icon(Icons.Default.AccountCircle, contentDescription = "Avatar por defecto") }
                            )
                        }
                        IconButton(onClick = onLogout) {
                            Icon(Icons.Filled.ExitToApp, contentDescription = "Cerrar Sesión")
                        }
                    } else {
                        IconButton(onClick = onNavigateToAuth) {
                            Icon(Icons.Filled.Person, contentDescription = "Iniciar Sesión")
                        }
                    }
                    IconButton(onClick = onNavigateToCart) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val phoneNumber = "+56912345678" // Reemplaza con el número de soporte
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")
                }
                context.startActivity(intent)
            }) {
                Icon(Icons.Filled.SupportAgent, contentDescription = "Soporte Técnico")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = searchText,
                onValueChange = productViewModel::onSearchTextChange,
                label = { Text("Buscar producto...") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            )
            CategoryChips(
                selectedCategory = selectedCategory,
                onCategorySelected = { productViewModel.onCategoryChange(it) }
            )
            LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        currentUser = currentUser,
                        onAddToCart = { quantity ->
                            cartViewModel.addToCart(product, quantity)
                            scope.launch {
                                snackbarHostState.showSnackbar("Se añadió ${product.name} (x$quantity) al carrito")
                            }
                        },
                        onNavigateToAuth = onNavigateToAuth
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChips(selectedCategory: Category?, onCategorySelected: (Category?) -> Unit) {
    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("Todos") },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        items(Category.values()) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category.name.replace('_', ' ').lowercase().replaceFirstChar { it.titlecase() }) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    currentUser: User?,
    onAddToCart: (Int) -> Unit,
    onNavigateToAuth: () -> Unit
) {
    var quantity by remember { mutableStateOf(1) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showLoginDialog by remember { mutableStateOf(false) }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Confirmar Acción") },
            text = { Text("¿Estás seguro de que quieres añadir $quantity producto(s) al carrito?") },
            confirmButton = {
                Button(
                    onClick = {
                        onAddToCart(quantity)
                        showConfirmDialog = false
                    }
                ) {
                    Text("Añadir")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showConfirmDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showLoginDialog) {
        AlertDialog(
            onDismissRequest = { showLoginDialog = false },
            title = { Text("Sesión Requerida") },
            text = { Text("Debes iniciar sesión para añadir productos al carrito.") },
            confirmButton = {
                Button(
                    onClick = {
                        onNavigateToAuth()
                        showLoginDialog = false
                    }
                ) {
                    Text("Iniciar Sesión")
                }
            },
            dismissButton = {
                Button(onClick = { showLoginDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray.copy(alpha = 0.3f)
        )
    ) {
        Column {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen de ${product.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(180.dp),
                loading = { Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() } },
                error = { /* No mostrar nada en caso de error */ }
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(product.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                val averageRating = if (product.ratings.isNotEmpty()) product.ratings.average() else 0.0
                RatingBar(currentRating = averageRating.toInt(), onRatingChanged = {})
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.category.name.replace('_', ' ').lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(product.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("$${product.price.toInt()}", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (quantity > 1) quantity-- }) {
                            Icon(Icons.Filled.Remove, contentDescription = "Restar cantidad")
                        }
                        Text(text = quantity.toString(), style = MaterialTheme.typography.bodyLarge)
                        IconButton(onClick = { quantity++ }) {
                            Icon(Icons.Filled.Add, contentDescription = "Añadir cantidad")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (currentUser != null) {
                            showConfirmDialog = true
                        } else {
                            showLoginDialog = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Añadir ${quantity} al carrito")
                }
            }
        }
    }
}
