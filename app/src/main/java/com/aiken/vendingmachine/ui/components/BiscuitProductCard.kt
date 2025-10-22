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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aiken.vendingmachine.data.model.Product
import com.aiken.vendingmachine.data.model.ProductCategory
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
                    text = "$${product.price}",
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