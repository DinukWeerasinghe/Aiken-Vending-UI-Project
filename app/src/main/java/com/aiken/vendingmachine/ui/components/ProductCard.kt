package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aiken.vendingmachine.data.model.Product
import com.aiken.vendingmachine.data.model.ProductCategory
import com.aiken.vendingmachine.data.model.PromoSlide
import com.aiken.vendingmachine.data.model.PromoType
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: Product,
    onProductClick: (Product) -> Unit,
    onAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val isAvailable = product.stockLevel > 0 && product.isAvailable

    Card(
        onClick = { if (isAvailable) onProductClick(product) },
        modifier = modifier
            .size(180.dp, 200.dp)
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

                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = product.category.name,
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = when (product.category) {
                            //ProductCategory.DRINKS -> DrinksColor.copy(alpha = 0.1f)
                            //ProductCategory.SNACKS -> SnacksColor.copy(alpha = 0.1f)
                            //ProductCategory.HEALTHY -> HealthyColor.copy(alpha = 0.1f)
                            else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        },
                        labelColor = when (product.category) {
                            //ProductCategory.DRINKS -> DrinksColor
                            //ProductCategory.SNACKS -> SnacksColor
                            //ProductCategory.HEALTHY -> HealthyColor
                            else -> MaterialTheme.colorScheme.primary
                        }
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
                text = "$${product.price}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Add to Cart Button
            androidx.compose.material3.FilledTonalButton(
                onClick = { onAddToCart(product) },
                enabled = isAvailable,
                modifier = Modifier.fillMaxWidth()
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopCarouselSection(
    promoSlides: List<PromoSlide>,
    currentSlideIndex: Int,
    onSlideChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = currentSlideIndex,
        pageCount = { promoSlides.size }
    )

    // Auto-advance carousel
    LaunchedEffect(pagerState.currentPage) {
        while (true) {
            delay(5000) // 5 seconds
            val nextPage = (pagerState.currentPage + 1) % promoSlides.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    // Update current slide index
    LaunchedEffect(pagerState.currentPage) {
        onSlideChange(pagerState.currentPage)
    }

    var isMuted by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val slide = promoSlides[page]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        alpha = 1f - pageOffset / 3f
                    }
            ) {
                AsyncImage(
                    model = slide.imageUrl,
                    contentDescription = slide.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    slide.backgroundColor.copy(alpha = 0.7f),
                                    slide.backgroundColor.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Content
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    Text(
                        text = slide.title,
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = slide.subtitle,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        // Mute button
        IconButton(
            onClick = { isMuted = !isMuted },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(48.dp)
        ) {
            Icon(
                imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                contentDescription = if (isMuted) "Unmute" else "Mute",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        // Page indicators
        androidx.compose.foundation.layout.Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            repeat(promoSlides.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) {
                    Color.White
                } else {
                    Color.White.copy(alpha = 0.5f)
                }
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(color)
                        .size(12.dp)
                )
            }
        }
    }
}

// Preview Functions for ProductCard
@Preview(showBackground = true)
@Composable
private fun ProductCardInStockPreview() {
    VendingMachineTheme {
        ProductCard(
            product = createMockProduct(),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardOutOfStockPreview() {
    VendingMachineTheme {
        ProductCard(
            product = createMockProduct(stockLevel = 0, isAvailable = false),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardCookiesPreview() {
    VendingMachineTheme {
        ProductCard(
            product = createMockProduct(
                name = "Chocolate Chip Cookies",
                category = ProductCategory.COOKIES,
                price = 2.50
            ),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardCreamBiscuitsPreview() {
    VendingMachineTheme {
        ProductCard(
            product = createMockProduct(
                name = "Vanilla Cream Biscuits",
                category = ProductCategory.CREAM_BISCUITS,
                price = 1.75
            ),
            onProductClick = { },
            onAddToCart = { }
        )
    }
}

// Preview Functions for TopCarouselSection
@Preview(showBackground = true, widthDp = 400, heightDp = 200)
@Composable
private fun TopCarouselSectionPreview() {
    VendingMachineTheme {
        TopCarouselSection(
            promoSlides = listOf(
                PromoSlide(
                    id = 1,
                    title = "New Arrivals",
                    subtitle = "Discover our latest biscuits",
                    imageUrl = "https://example.com/promo1.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF4CAF50)
                ),
                PromoSlide(
                    id = 2,
                    title = "Special Offer",
                    subtitle = "20% off all cookies",
                    imageUrl = "https://example.com/promo2.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF2196F3)
                ),
                PromoSlide(
                    id = 3,
                    title = "Limited Edition",
                    subtitle = "Try our new chocolate range",
                    imageUrl = "https://example.com/promo3.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF9C27B0)
                )
            ),
            currentSlideIndex = 0,
            onSlideChange = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 200)
@Composable
private fun TopCarouselSectionSingleSlidePreview() {
    VendingMachineTheme {
        TopCarouselSection(
            promoSlides = listOf(
                PromoSlide(
                    id = 1,
                    title = "Maliban Premium",
                    subtitle = "Quality since 1954",
                    imageUrl = "https://example.com/maliban-premium.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFFF57C00)
                )
            ),
            currentSlideIndex = 0,
            onSlideChange = { }
        )
    }
}

// Helper function to create mock product for previews
private fun createMockProduct(
    id: Int = 1,
    name: String = "Glucose Biscuits",
    description: String = "Classic glucose biscuits for energy",
    price: Double = 1.50,
    category: ProductCategory = ProductCategory.GLUCOSE,
    imageUrl: String = "https://example.com/glucose-biscuits.jpg",
    stockLevel: Int = 15,
    shelfPosition: String = "A1",
    nutritionInfo: com.aiken.vendingmachine.data.model.NutritionInfo = com.aiken.vendingmachine.data.model.NutritionInfo(
        calories = 450,
        ingredients = "Wheat Flour, Sugar, Glucose Syrup"
    ),
    weight: String = "200g",
    tags: List<String> = listOf("glucose", "energy", "classic"),
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