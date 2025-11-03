package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aiken.vendingmachine.data.model.PromoSlide
import com.aiken.vendingmachine.data.model.PromoType
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EnhancedCarousel(
    promoSlides: List<PromoSlide>,
    currentSlideIndex: Int,
    onSlideChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = currentSlideIndex,
        pageCount = { promoSlides.size }
    )

    // Auto-advance carousel (only for image slides)
    LaunchedEffect(pagerState.currentPage) {
        val currentSlide = promoSlides.getOrNull(pagerState.currentPage)
        if (currentSlide?.type == PromoType.IMAGE) {
            while (true) {
                delay(5000) // 5 seconds
                val nextPage = (pagerState.currentPage + 1) % promoSlides.size
                pagerState.animateScrollToPage(nextPage)
            }
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

            when (slide.type) {
                PromoType.VIDEO -> {
                    // Video slide with thumbnail and play indicator
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Show thumbnail image
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
                                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            slide.backgroundColor.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )

                        // Play button overlay for video
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Surface(
                                color = Color.Black.copy(alpha = 0.6f),
                                shape = MaterialTheme.shapes.large,
                                modifier = Modifier.size(80.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play video",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(20.dp)
                                )
                            }
                        }

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

                            androidx.compose.animation.AnimatedVisibility(
                                visible = true,
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text(
                                    text = slide.subtitle,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color.White,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }

                            androidx.compose.animation.AnimatedVisibility(
                                visible = true,
                                modifier = Modifier.padding(top = 24.dp)
                            ) {
                                Text(
                                    text = "Tap to play advertisement",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
                else -> {
                    // Image slide
                    Box(
                        modifier = Modifier.fillMaxSize()
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
                                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
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
            }
        }

        // Mute button (for future audio implementation)
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

        // Enhanced page indicators with video indicator
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            repeat(promoSlides.size) { iteration ->
                val slide = promoSlides[iteration]
                val color = if (pagerState.currentPage == iteration) {
                    Color.White
                } else {
                    Color.White.copy(alpha = 0.5f)
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(
                            width = if (slide.type == PromoType.VIDEO) 20.dp else 12.dp,
                            height = 12.dp
                        )
                        .clip(MaterialTheme.shapes.small)
                        .background(color)
                ) {
                    if (slide.type == PromoType.VIDEO && pagerState.currentPage == iteration) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Video",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(8.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

// Preview Functions
@Preview(showBackground = true, widthDp = 400, heightDp = 300)
@Composable
private fun EnhancedCarouselMixedPreview() {
    VendingMachineTheme {
        EnhancedCarousel(
            promoSlides = listOf(
                PromoSlide(
                    id = 1,
                    title = "New Chocolate Collection",
                    subtitle = "Discover our premium range",
                    imageUrl = "https://example.com/chocolate.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF8B4513)
                ),
                PromoSlide(
                    id = 2,
                    title = "Special Advertisement",
                    subtitle = "Watch our story",
                    imageUrl = "https://example.com/video-thumb.jpg",
                    type = PromoType.VIDEO,
                    backgroundColor = Color(0xFF2E8B57)
                ),
                PromoSlide(
                    id = 3,
                    title = "Limited Time Offer",
                    subtitle = "50% off selected items",
                    imageUrl = "https://example.com/sale.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFFDC143C)
                )
            ),
            currentSlideIndex = 0,
            onSlideChange = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 300)
@Composable
private fun EnhancedCarouselVideoFirstPreview() {
    VendingMachineTheme {
        EnhancedCarousel(
            promoSlides = listOf(
                PromoSlide(
                    id = 1,
                    title = "Maliban Story",
                    subtitle = "Our heritage since 1954",
                    imageUrl = "https://example.com/heritage.jpg",
                    type = PromoType.VIDEO,
                    backgroundColor = Color(0xFF1E3A8A)
                ),
                PromoSlide(
                    id = 2,
                    title = "Premium Ingredients",
                    subtitle = "Quality you can taste",
                    imageUrl = "https://example.com/quality.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF7C2D12)
                )
            ),
            currentSlideIndex = 0,
            onSlideChange = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 300)
@Composable
private fun EnhancedCarouselImagesOnlyPreview() {
    VendingMachineTheme {
        EnhancedCarousel(
            promoSlides = listOf(
                PromoSlide(
                    id = 1,
                    title = "Cream Biscuits",
                    subtitle = "Soft and delicious",
                    imageUrl = "https://example.com/cream-biscuits.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFFF59E0B)
                ),
                PromoSlide(
                    id = 2,
                    title = "Glucose Range",
                    subtitle = "Energy for your day",
                    imageUrl = "https://example.com/glucose.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF10B981)
                ),
                PromoSlide(
                    id = 3,
                    title = "Chocolate Treats",
                    subtitle = "Indulge yourself",
                    imageUrl = "https://example.com/chocolate-treats.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF8B5CF6)
                )
            ),
            currentSlideIndex = 1,
            onSlideChange = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 300)
@Composable
private fun EnhancedCarouselSingleSlidePreview() {
    VendingMachineTheme {
        EnhancedCarousel(
            promoSlides = listOf(
                PromoSlide(
                    id = 1,
                    title = "Welcome to Maliban",
                    subtitle = "Sri Lanka's Favorite Biscuits",
                    imageUrl = "https://example.com/welcome.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF0369A1)
                )
            ),
            currentSlideIndex = 0,
            onSlideChange = { }
        )
    }
}

// Helper function to create mock PromoSlide for previews
private fun createMockPromoSlide(
    id: Int = 1,
    title: String = "Promo Title",
    subtitle: String = "Promo Subtitle",
    imageUrl: String = "https://example.com/image.jpg",
    type: PromoType = PromoType.IMAGE,
    backgroundColor: Color = Color(0xFF4F46E5)
): PromoSlide {
    return PromoSlide(
        id = id,
        title = title,
        subtitle = subtitle,
        imageUrl = imageUrl,
        type = type,
        backgroundColor = backgroundColor
    )
}