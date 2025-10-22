package com.aiken.vendingmachine.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aiken.vendingmachine.ui.screens.CartScreen
import com.aiken.vendingmachine.ui.screens.CheckoutScreen
import com.aiken.vendingmachine.ui.screens.IdleScreen
import com.aiken.vendingmachine.ui.screens.ProcessingScreen
import com.aiken.vendingmachine.ui.screens.SuccessScreen
import com.aiken.vendingmachine.ui.viewmodel.VendingViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.aiken.vendingmachine.ui.screens.BiscuitProductDetailScreen
import com.aiken.vendingmachine.ui.screens.MalibanHomeScreen

@Preview
@Composable
fun VendingMachineNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: VendingViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            MalibanHomeScreen(
                onProductClick = { product ->
                    viewModel.selectProduct(product)
                    navController.navigate(Screen.ProductDetail.route)
                },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                },
                viewModel = viewModel
            )
        }

        composable(Screen.ProductDetail.route) {
            viewModel.uiState.collectAsState().value.selectedProduct?.let { product ->
                BiscuitProductDetailScreen(
                    product = product,
                    onClose = { navController.popBackStack() },
                    onAddToCart = { product, quantity ->
                        repeat(quantity) {
                            viewModel.addToCart(product)
                        }
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(Screen.Cart.route) {
            CartScreen(
                onContinueShopping = { navController.popBackStack() },
                onCheckout = { navController.navigate(Screen.Checkout.route) },
                viewModel = viewModel
            )
        }

        composable(Screen.Checkout.route) {
            CheckoutScreen(
                onCancel = { navController.popBackStack() },
                onConfirmPayment = { method ->
                    viewModel.processPayment(method)
                    navController.navigate(Screen.Processing.route)
                },
                viewModel = viewModel
            )
        }

        composable(Screen.Processing.route) {
            ProcessingScreen(
                onProcessingComplete = {
                    navController.navigate(Screen.Success.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                },
                viewModel = viewModel
            )
        }

        composable(Screen.Success.route) {
            SuccessScreen(
                onDone = {
                    viewModel.completePurchase()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Idle.route) {
            IdleScreen(
                onTouchToStart = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProductDetail : Screen("product_detail")
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object Processing : Screen("processing")
    object Success : Screen("success")
    object Idle : Screen("idle")
}