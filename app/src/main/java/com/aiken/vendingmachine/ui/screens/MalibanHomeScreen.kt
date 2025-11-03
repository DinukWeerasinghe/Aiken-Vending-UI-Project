package com.aiken.vendingmachine.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aiken.vendingmachine.ui.components.BiscuitCategoryFilterBar
import com.aiken.vendingmachine.ui.components.BiscuitProductCard
import com.aiken.vendingmachine.ui.components.BottomCartBar
import com.aiken.vendingmachine.ui.components.VideoCarousel
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme
import com.aiken.vendingmachine.ui.viewmodel.VendingViewModel
import androidx.compose.runtime.collectAsState
import com.aiken.vendingmachine.data.model.Product

@Composable
fun MalibanHomeScreen(
    onProductClick: (Product) -> Unit,
    onCartClick: () -> Unit,
    viewModel: VendingViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        // Reset any navigation state when returning home
        viewModel.clearSelectedProduct()
    }

    Scaffold(
        bottomBar = {
            BottomCartBar(
                itemCount = uiState.cartItemCount,
                totalPrice = uiState.totalPrice,
                onCheckoutClick = onCartClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Top Carousel Section (40% of screen) with direct video support
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(768.dp) // 40% of 1920
            ) {
                VideoCarousel(
                    promoSlides = uiState.promoSlides,
                    currentSlideIndex = uiState.currentSlideIndex,
                    onSlideChange = { viewModel.updateCurrentSlide(it) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Category Filter for Biscuits
            BiscuitCategoryFilterBar(
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { viewModel.filterByCategory(it) }
            )

            // Product Grid (50% of screen)
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(960.dp) // 50% of 1920
                    .padding(8.dp)
            ) {
                items(uiState.products) { product ->
                    BiscuitProductCard(
                        product = product,
                        onProductClick = onProductClick,
                        onAddToCart = { viewModel.addToCart(it) }
                    )
                }
            }
        }
    }
}

// Preview Functions
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MalibanHomeScreenPreview() {
    VendingMachineTheme {
        MalibanHomeScreen(
            onProductClick = { _ -> },
            onCartClick = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=1080px,height=1920px")
@Composable
private fun MalibanHomeScreenTabletPreview() {
    VendingMachineTheme {
        MalibanHomeScreen(
            onProductClick = { _ -> },
            onCartClick = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
private fun MalibanHomeScreenMobilePreview() {
    VendingMachineTheme {
        MalibanHomeScreen(
            onProductClick = { _ -> },
            onCartClick = { }
        )
    }
}