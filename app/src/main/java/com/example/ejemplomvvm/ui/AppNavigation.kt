package com.example.ejemplomvvm.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.ejemplomvvm.ui.home.HomeScreen
import com.example.ejemplomvvm.ui.product.ProductScreen
import com.example.ejemplomvvm.ui.profile.ProfileScreen
import com.example.ejemplomvvm.ui.SessionViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberAnimatedNavController()
    val cartViewModel: CartViewModel = viewModel()
    val sessionViewModel: SessionViewModel = viewModel()

    AnimatedNavHost(
        navController = navController, 
        startDestination = "home",
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
    ) {
        composable("home") {
            val currentUser by sessionViewModel.currentUser.collectAsState()
            HomeScreen(
                currentUser = currentUser,
                onExploreCatalog = { navController.navigate("products") },
                onLogin = { navController.navigate("auth") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }

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
                    navController.popBackStack(route = "home", inclusive = false)
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
