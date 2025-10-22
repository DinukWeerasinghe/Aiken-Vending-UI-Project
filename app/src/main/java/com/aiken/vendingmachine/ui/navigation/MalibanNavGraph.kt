package com.aiken.vendingmachine.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aiken.vendingmachine.ui.screens.*
import androidx.compose.runtime.collectAsState

@Composable
fun MalibanVendingMachineNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: com.aiken.vendingmachine.ui.viewmodel.VendingViewModel = viewModel()
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

        // ... rest of the navigation remains the same for Cart, Checkout, etc.
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
            SuccessScreen {
                viewModel.completePurchase()
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Idle.route) {
            IdleScreen {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }
        }
    }
}