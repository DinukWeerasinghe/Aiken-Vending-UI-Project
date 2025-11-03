package com.aiken.vendingmachine.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.aiken.vendingmachine.data.model.Product
import com.aiken.vendingmachine.data.model.ProductCategory
import com.aiken.vendingmachine.data.model.NutritionInfo
import com.aiken.vendingmachine.ui.components.NutritionBadge
import com.aiken.vendingmachine.ui.components.QuantitySelector
import com.aiken.vendingmachine.ui.components.StockIndicator
import com.aiken.vendingmachine.ui.theme.Primary
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme

@Composable
fun BiscuitProductDetailScreen(
    product: Product,
    onClose: () -> Unit,
    onAddToCart: (Product, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableIntStateOf(1) }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with close button and brand
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Brand header
                    Surface(
                        color = Primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = product.brand,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                // Product Image
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(32.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Product Info
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.weight(1f)
                        )

                        StockIndicator(
                            isInStock = product.stockLevel > 0 && product.isAvailable,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.weight,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$${product.price}",
                        style = MaterialTheme.typography.displaySmall,
                        color = Primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Nutrition badges
                    NutritionBadge(
                        nutritionInfo = product.nutritionInfo,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Product Information Cards
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Ingredients
                        ProductInfoCard(
                            title = "Ingredients",
                            content = product.nutritionInfo.ingredients
                        )

                        // Nutritional Information
                        ProductInfoCard(
                            title = "Nutritional Information",
                            content = """
                                Calories: ${product.nutritionInfo.calories} kcal
                                Weight: ${product.weight}
                                Shelf Position: ${product.shelfPosition}
                                Stock Available: ${product.stockLevel} units
                            """.trimIndent()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Quantity Selector
                    Text(
                        text = "Quantity",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    QuantitySelector(
                        quantity = quantity,
                        onQuantityChange = { newQuantity ->
                            quantity = newQuantity.coerceIn(1, product.stockLevel)
                        },
                        maxQuantity = product.stockLevel,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Add to Cart Button
                    Button(
                        onClick = { onAddToCart(product, quantity) },
                        enabled = product.stockLevel > 0 && product.isAvailable,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary
                        )
                    ) {
                        Text(
                            text = if (product.stockLevel > 0) {
                                "Add $quantity to Cart - $${String.format("%.2f", product.price * quantity)}"
                            } else {
                                "Out of Stock"
                            },
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }

    LaunchedEffect(product) {
        quantity = 1 // Reset quantity when product changes
    }
}

@Composable
fun ProductInfoCard(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.3
            )
        }
    }
}

// Preview Functions
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BiscuitProductDetailScreenPreview() {
    VendingMachineTheme {
        BiscuitProductDetailScreen(
            product = createMockProduct(),
            onClose = {},
            onAddToCart = { _, _ -> }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BiscuitProductDetailScreenOutOfStockPreview() {
    VendingMachineTheme {
        BiscuitProductDetailScreen(
            product = createMockProduct(stockLevel = 0),
            onClose = {},
            onAddToCart = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductInfoCardPreview() {
    VendingMachineTheme {
        ProductInfoCard(
            title = "Ingredients",
            content = "Flour, Chocolate Chips, Butter, Eggs, Vanilla Extract, Baking Soda, Salt"
        )
    }
}

// Helper function to create mock product for previews
private fun createMockProduct(
    id: Int = 6,
    stockLevel: Int = 15,
    isAvailable: Boolean = true
): Product {
    return Product(
        id = id,
        name = "Chocolate Chip Cookies",
        description = "Buttery cookies with real chocolate chips",
        price = 2.00,
        category = ProductCategory.COOKIES,
        imageUrl = "https://www.malibangroup.com/images/products/chocolate-chip.jpg",
        stockLevel = stockLevel,
        shelfPosition = "C1",
        nutritionInfo = NutritionInfo(
            calories = 510,
            ingredients = "Wheat Flour, Chocolate Chips, Butter, Sugar, Eggs"
        ),
        weight = "150g",
        tags = listOf("cookies", "chocolate-chip", "buttery"),
        isAvailable = isAvailable,
        brand = "Maliban"
    )
}