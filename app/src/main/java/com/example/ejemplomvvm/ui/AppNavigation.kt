package com.example.ejemplomvvm.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ejemplomvvm.data.Purchase
import com.example.ejemplomvvm.data.UserRepository
import com.example.ejemplomvvm.ui.auth.AuthScreen
import com.example.ejemplomvvm.ui.auth.LoginScreen
import com.example.ejemplomvvm.ui.auth.RegisterScreen
import com.example.ejemplomvvm.ui.cart.CartScreen
import com.example.ejemplomvvm.ui.cart.CartViewModel
import com.example.ejemplomvvm.ui.checkout.CheckoutScreen
import com.example.ejemplomvvm.ui.checkout.ReceiptScreen
import com.example.ejemplomvvm.ui.history.HistoryScreen
import com.example.ejemplomvvm.ui.product.ProductScreen
import com.example.ejemplomvvm.ui.profile.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()
    val sessionViewModel: SessionViewModel = viewModel()

    NavHost(navController = navController, startDestination = "products") {
        composable("products") {
            val currentUser by sessionViewModel.currentUser.collectAsState()
            ProductScreen(
                cartViewModel = cartViewModel,
                currentUser = currentUser,
                onNavigateToCart = { navController.navigate("cart") },
                onNavigateToAuth = { navController.navigate("auth") },
                onNavigateToProfile = { navController.navigate("profile") },
                onLogout = { sessionViewModel.logout() }
            )
        }

        composable("profile") {
            val currentUser by sessionViewModel.currentUser.collectAsState()
            currentUser?.let {
                ProfileScreen(
                    currentUser = it,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToHistory = { navController.navigate("history") }
                )
            }
        }

        composable("history") {
            HistoryScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("cart") {
            val currentUser by sessionViewModel.currentUser.collectAsState()
            CartScreen(
                cartViewModel = cartViewModel,
                currentUser = currentUser,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCheckout = { navController.navigate("checkout") }
            )
        }

        composable("checkout") {
            CheckoutScreen(
                onNavigateBack = { navController.popBackStack() },
                onPurchaseComplete = {
                    val currentUser = sessionViewModel.currentUser.value // Acceso directo al valor
                    val cartItems = cartViewModel.cartItems.value
                    val total = cartViewModel.getTotalPrice(currentUser)
                    currentUser?.let {
                        UserRepository.addPurchaseToHistory(it.email, Purchase(cartItems, total))
                    }
                    navController.navigate("receipt")
                }
            )
        }

        composable("receipt") {
            val currentUser by sessionViewModel.currentUser.collectAsState()
            ReceiptScreen(
                cartViewModel = cartViewModel,
                currentUser = currentUser,
                onNavigateToHome = {
                    cartViewModel.clearCart()
                    navController.popBackStack(route = "products", inclusive = false)
                }
            )
        }

        composable("auth") {
            AuthScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("login") {
            LoginScreen(
                onNavigateBack = { navController.popBackStack() },
                onLoginSuccess = {
                    navController.popBackStack(route = "auth", inclusive = true)
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.popBackStack(route = "auth", inclusive = true)
                }
            )
        }
    }
}
