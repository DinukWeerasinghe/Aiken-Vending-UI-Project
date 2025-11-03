package com.aiken.vendingmachine.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aiken.vendingmachine.data.model.PaymentMethod
import com.aiken.vendingmachine.data.model.Product
import com.aiken.vendingmachine.data.model.ProductCategory
import com.aiken.vendingmachine.data.model.CartItem
import com.aiken.vendingmachine.data.model.NutritionInfo
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme
import com.aiken.vendingmachine.ui.viewmodel.VendingViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun CheckoutScreen(
    onCancel: () -> Unit,
    onConfirmPayment: (PaymentMethod) -> Unit,
    viewModel: VendingViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Select Payment Method",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Payment methods grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(PaymentMethod.entries.toList()) { method ->
                PaymentMethodCard(
                    method = method,
                    isSelected = uiState.selectedPaymentMethod == method,
                    onClick = { viewModel.selectPaymentMethod(method) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Order summary
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Order Summary",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                uiState.cartItems.forEach { item ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("${item.quantity} × ${item.product.name}")
                        Text("$${String.format("%.2f", item.product.price * item.quantity)}")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Total Amount",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$${String.format("%.2f", uiState.totalPrice)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    uiState.selectedPaymentMethod?.let { onConfirmPayment(it) }
                },
                enabled = uiState.selectedPaymentMethod != null,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Confirm Payment")
            }
        }
    }
}

@Composable
fun PaymentMethodCard(
    method: PaymentMethod,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .size(200.dp, 200.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = when (method) {
                    PaymentMethod.CASH -> Icons.Default.CurrencyExchange
                    PaymentMethod.CARD -> Icons.Default.CreditCard
                    PaymentMethod.DIGITAL_WALLET -> Icons.Default.AccountBalanceWallet
                    PaymentMethod.QR_CODE -> Icons.Default.QrCode
                },
                contentDescription = method.name,
                modifier = Modifier.size(48.dp),
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when (method) {
                    PaymentMethod.CASH -> "Cash"
                    PaymentMethod.CARD -> "Credit/Debit Card"
                    PaymentMethod.DIGITAL_WALLET -> "Digital Wallet"
                    PaymentMethod.QR_CODE -> "QR Code"
                },
                style = MaterialTheme.typography.titleMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

// Preview Functions
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CheckoutScreenPreview() {
    VendingMachineTheme {
        CheckoutScreen(
            onCancel = { },
            onConfirmPayment = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CheckoutScreenWithItemsPreview() {
    VendingMachineTheme {
        CheckoutScreen(
            onCancel = { },
            onConfirmPayment = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, widthDp = 400, heightDp = 800)
@Composable
private fun CheckoutScreenTabletPreview() {
    VendingMachineTheme {
        CheckoutScreen(
            onCancel = { },
            onConfirmPayment = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, widthDp = 320, heightDp = 640)
@Composable
private fun CheckoutScreenMobilePreview() {
    VendingMachineTheme {
        CheckoutScreen(
            onCancel = { },
            onConfirmPayment = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PaymentMethodCardSelectedPreview() {
    VendingMachineTheme {
        PaymentMethodCard(
            method = PaymentMethod.CARD,
            isSelected = true,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PaymentMethodCardUnselectedPreview() {
    VendingMachineTheme {
        PaymentMethodCard(
            method = PaymentMethod.CASH,
            isSelected = false,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PaymentMethodCardAllMethodsPreview() {
    VendingMachineTheme {
        Column {
            PaymentMethod.values().forEach { method ->
                PaymentMethodCard(
                    method = method,
                    isSelected = false,
                    onClick = { }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// Helper function to create mock cart items for testing
private fun createMockCartItems(): List<CartItem> {
    val product1 = Product(
        id = 1,
        name = "Chocolate Chip Cookies",
        description = "Delicious chocolate chip cookies",
        price = 2.50,
        category = ProductCategory.COOKIES,
        imageUrl = "https://example.com/cookies.jpg",
        stockLevel = 15,
        shelfPosition = "A1",
        nutritionInfo = NutritionInfo(
            calories = 150,
            ingredients = "Flour, Chocolate, Sugar"
        ),
        weight = "150g",
        tags = listOf("cookies", "chocolate"),
        isAvailable = true,
        brand = "Maliban"
    )

    val product2 = Product(
        id = 2,
        name = "Cream Biscuits",
        description = "Soft cream filled biscuits",
        price = 1.75,
        category = ProductCategory.CREAM_BISCUITS,
        imageUrl = "https://example.com/cream-biscuits.jpg",
        stockLevel = 8,
        shelfPosition = "B2",
        nutritionInfo = NutritionInfo(
            calories = 120,
            ingredients = "Flour, Cream, Sugar"
        ),
        weight = "200g",
        tags = listOf("biscuits", "cream"),
        isAvailable = true,
        brand = "Maliban"
    )

    return listOf(
        CartItem(product1, 2),
        CartItem(product2, 1)
    )
}