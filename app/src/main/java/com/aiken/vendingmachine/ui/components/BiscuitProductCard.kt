package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aiken.vendingmachine.data.model.Product
import com.aiken.vendingmachine.data.model.ProductCategory
import com.aiken.vendingmachine.data.model.NutritionInfo
import com.aiken.vendingmachine.ui.theme.*

@Composable
fun BiscuitProductCard(
    product: Product,
    onProductClick: (Product) -> Unit,
    onAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val isAvailable = product.stockLevel > 0 && product.isAvailable

    Card(
        onClick = { if (isAvailable) onProductClick(product) },
        modifier = modifier
            .size(180.dp, 220.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        enabled = isAvailable
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Header with stock indicator and category
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                StockIndicator(
                    isInStock = isAvailable,
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                // Brand badge
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = product.brand,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Primary.copy(alpha = 0.1f),
                        labelColor = Primary
                    ),
                    border = null,
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Product Image
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product Info
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = product.weight,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Rs ${product.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )

                // Category color dot
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(getBiscuitCategoryColor(product.category))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Add to Cart Button
            androidx.compose.material3.FilledTonalButton(
                onClick = { onAddToCart(product) },
                enabled = isAvailable,
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.filledTonalButtonColors(
                    containerColor = Primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to cart",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = if (isAvailable) "Add" else "Out of Stock",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

fun getBiscuitCategoryColor(category: ProductCategory): androidx.compose.ui.graphics.Color {
    return when (category) {
        ProductCategory.CREAM_BISCUITS -> CreamBiscuitsColor
        ProductCategory.GLUCOSE -> GlucoseColor
        ProductCategory.COOKIES -> CookiesColor
        ProductCategory.CRACKERS -> CrackersColor
        ProductCategory.WAFERS -> WafersColor
        ProductCategory.CHOCOLATE -> ChocolateColor
        ProductCategory.ALL -> Primary
    }
}

// Preview Functions
@Preview(showBackground = true)
@Composable
private fun BiscuitProductCardCreamBiscuitsPreview() {
    VendingMachineTheme {
        BiscuitProductCard(
            product = createMockBiscuitProduct(
                name = "Vanilla Cream",
                category = ProductCategory.CREAM_BISCUITS,
                brand = "Maliban",
                price = 1.75,
                weight = "200g"
            ),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BiscuitProductCardGlucosePreview() {
    VendingMachineTheme {
        BiscuitProductCard(
            product = createMockBiscuitProduct(
                name = "Glucose Energy",
                category = ProductCategory.GLUCOSE,
                brand = "Maliban",
                price = 1.50,
                weight = "180g"
            ),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BiscuitProductCardCookiesPreview() {
    VendingMachineTheme {
        BiscuitProductCard(
            product = createMockBiscuitProduct(
                name = "Chocolate Chip Cookies",
                category = ProductCategory.COOKIES,
                brand = "Maliban",
                price = 2.25,
                weight = "150g"
            ),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BiscuitProductCardCrackersPreview() {
    VendingMachineTheme {
        BiscuitProductCard(
            product = createMockBiscuitProduct(
                name = "Cheese Crackers",
                category = ProductCategory.CRACKERS,
                brand = "Maliban",
                price = 1.80,
                weight = "120g"
            ),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BiscuitProductCardWafersPreview() {
    VendingMachineTheme {
        BiscuitProductCard(
            product = createMockBiscuitProduct(
                name = "Strawberry Wafers",
                category = ProductCategory.WAFERS,
                brand = "Maliban",
                price = 2.00,
                weight = "100g"
            ),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BiscuitProductCardChocolatePreview() {
    VendingMachineTheme {
        BiscuitProductCard(
            product = createMockBiscuitProduct(
                name = "Chocolate Biscuits",
                category = ProductCategory.CHOCOLATE,
                brand = "Maliban",
                price = 2.50,
                weight = "160g"
            ),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BiscuitProductCardOutOfStockPreview() {
    VendingMachineTheme {
        BiscuitProductCard(
            product = createMockBiscuitProduct(
                name = "Vanilla Cream",
                category = ProductCategory.CREAM_BISCUITS,
                brand = "Maliban",
                price = 1.75,
                weight = "200g",
                stockLevel = 0,
                isAvailable = false
            ),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BiscuitProductCardLowStockPreview() {
    VendingMachineTheme {
        BiscuitProductCard(
            product = createMockBiscuitProduct(
                name = "Glucose Energy",
                category = ProductCategory.GLUCOSE,
                brand = "Maliban",
                price = 1.50,
                weight = "180g",
                stockLevel = 2
            ),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

// Helper function to create mock biscuit product for previews
private fun createMockBiscuitProduct(
    id: Int = 1,
    name: String = "Biscuit Product",
    description: String = "Delicious biscuits",
    price: Double = 1.50,
    category: ProductCategory = ProductCategory.CREAM_BISCUITS,
    imageUrl: String = "https://example.com/biscuit.jpg",
    stockLevel: Int = 15,
    shelfPosition: String = "A1",
    nutritionInfo: NutritionInfo = NutritionInfo(
        calories = 450,
        ingredients = "Wheat Flour, Sugar, Vegetable Oil"
    ),
    weight: String = "200g",
    tags: List<String> = listOf("biscuit", "snack"),
    isAvailable: Boolean = true,
    brand: String = "Maliban"
): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        price = price,
        category = category,
        imageUrl = imageUrl,
        stockLevel = stockLevel,
        shelfPosition = shelfPosition,
        nutritionInfo = nutritionInfo,
        weight = weight,
        tags = tags,
        isAvailable = isAvailable,
        brand = brand
    )
}