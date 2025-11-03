package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aiken.vendingmachine.data.model.PromoSlide
import com.aiken.vendingmachine.data.model.PromoType
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoCarousel(
    promoSlides: List<PromoSlide>,
    currentSlideIndex: Int,
    onSlideChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = currentSlideIndex,
        pageCount = { promoSlides.size }
    )

    var isMuted by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }

    // Auto-advance for image slides only
    LaunchedEffect(pagerState.currentPage, isPaused) {
        val currentSlide = promoSlides.getOrNull(pagerState.currentPage)
        if (currentSlide?.type == PromoType.IMAGE && !isPaused) {
            while (true) {
                delay(5000) // 5 seconds
                if (!isPaused) {
                    val nextPage = (pagerState.currentPage + 1) % promoSlides.size
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }
    }

    // Update current slide index
    LaunchedEffect(pagerState.currentPage) {
        onSlideChange(pagerState.currentPage)
    }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val slide = promoSlides[page]
            val pageOffset = (
                    (pagerState.currentPage - page) + pagerState
                        .currentPageOffsetFraction
                    ).absoluteValue

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = 1f - pageOffset / 3f
                    }
            ) {
                when (slide.type) {
                    PromoType.LOCAL_VIDEO -> {
                        // Direct video playback from assets
                        DirectVideoPlayer(
                            videoAssetPath = slide.videoAssetPath,
                            videoUrl = slide.videoUrl,
                            autoPlay = !isPaused && pagerState.currentPage == page,
                            loop = true,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Gradient overlay for text readability
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            slide.backgroundColor.copy(alpha = 0.3f),
                                            slide.backgroundColor.copy(alpha = 0.6f)
                                        )
                                    )
                                )
                        )
                    }
                    PromoType.VIDEO -> {
                        // Online video - show thumbnail with play button
                        AsyncImage(
                            model = slide.imageUrl,
                            contentDescription = slide.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Play button overlay
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
                    }
                    else -> {
                        // Image slide
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
                    }
                }

                // Content text (shown on all slide types)
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
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    androidx.compose.animation.AnimatedVisibility(
                        visible = slide.subtitle.isNotEmpty(),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(
                            text = slide.subtitle,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    // Video indicator for local videos
                    androidx.compose.animation.AnimatedVisibility(
                        visible = slide.type == PromoType.LOCAL_VIDEO && isPaused,
                        modifier = Modifier.padding(top = 24.dp)
                    ) {
                        Text(
                            text = "Video Paused - Tap to play",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // Controls overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top controls
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Play/Pause button (only for video slides)
                val currentSlide = promoSlides.getOrNull(pagerState.currentPage)
                if (currentSlide?.type == PromoType.LOCAL_VIDEO) {
                    IconButton(
                        onClick = { isPaused = !isPaused },
                        modifier = Modifier
                            .size(48.dp)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                            contentDescription = if (isPaused) "Play" else "Pause",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Mute button
                IconButton(
                    onClick = { isMuted = !isMuted },
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                        contentDescription = if (isMuted) "Unmute" else "Mute",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Spacer to push indicators to bottom
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))

            // Page indicators
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                repeat(promoSlides.size) { iteration ->
                    val slide = promoSlides[iteration]
                    val color = if (pagerState.currentPage == iteration) {
                        Color.White
                    } else {
                        Color.White.copy(alpha = 0.5f)
                    }

                    val size = when {
                        slide.type == PromoType.LOCAL_VIDEO -> 16.dp
                        slide.type == PromoType.VIDEO -> 14.dp
                        else -> 12.dp
                    }

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(size)
                            .clip(MaterialTheme.shapes.small)
                            .background(color)
                    ) {
                        // Video indicator icon
                        if (slide.type == PromoType.LOCAL_VIDEO && pagerState.currentPage == iteration) {
                            Icon(
                                imageVector = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                                contentDescription = "Video",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(10.dp)
                                    .align(Alignment.Center)
                            )
                        } else if (slide.type == PromoType.VIDEO && pagerState.currentPage == iteration) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Online Video",
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
}

// Preview Functions
@Preview(showBackground = true, widthDp = 400, heightDp = 250)
@Composable
private fun VideoCarouselMixedContentPreview() {
    VendingMachineTheme {
        VideoCarousel(
            promoSlides = listOf(
                PromoSlide(
                    id = 1,
                    title = "New Chocolate Range",
                    subtitle = "Premium quality chocolate biscuits",
                    imageUrl = "https://example.com/chocolate-banner.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF7B3F00)
                ),
                PromoSlide(
                    id = 2,
                    title = "Maliban Heritage",
                    subtitle = "Watch our story",
                    imageUrl = "https://example.com/video-thumb.jpg",
                    type = PromoType.VIDEO,
                    backgroundColor = Color(0xFF1E3A8A)
                ),
                PromoSlide(
                    id = 3,
                    title = "Special Promotion",
                    subtitle = "Limited time offers",
                    imageUrl = "https://example.com/promo-banner.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFFDC2626)
                )
            ),
            currentSlideIndex = 0,
            onSlideChange = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 250)
@Composable
private fun VideoCarouselImagesOnlyPreview() {
    VendingMachineTheme {
        VideoCarousel(
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
                    imageUrl = "https://example.com/glucose-banner.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF10B981)
                ),
                PromoSlide(
                    id = 3,
                    title = "Cookies Collection",
                    subtitle = "Crunchy and tasty",
                    imageUrl = "https://example.com/cookies-banner.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF8B5CF6)
                )
            ),
            currentSlideIndex = 1,
            onSlideChange = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 250)
@Composable
private fun VideoCarouselVideoFirstPreview() {
    VendingMachineTheme {
        VideoCarousel(
            promoSlides = listOf(
                PromoSlide(
                    id = 1,
                    title = "Product Demo",
                    subtitle = "See how it's made",
                    imageUrl = "https://example.com/demo-thumb.jpg",
                    type = PromoType.VIDEO,
                    backgroundColor = Color(0xFF059669)
                ),
                PromoSlide(
                    id = 2,
                    title = "Premium Ingredients",
                    subtitle = "Quality you can trust",
                    imageUrl = "https://example.com/quality-banner.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF7C2D12)
                )
            ),
            currentSlideIndex = 0,
            onSlideChange = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 250)
@Composable
private fun VideoCarouselSingleSlidePreview() {
    VendingMachineTheme {
        VideoCarousel(
            promoSlides = listOf(
                PromoSlide(
                    id = 1,
                    title = "Welcome to Maliban",
                    subtitle = "Sri Lanka's Favorite Biscuits Since 1954",
                    imageUrl = "https://example.com/welcome-banner.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFF0369A1)
                )
            ),
            currentSlideIndex = 0,
            onSlideChange = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 250)
@Composable
private fun VideoCarouselLocalVideoPreview() {
    VendingMachineTheme {
        VideoCarousel(
            promoSlides = listOf(
                PromoSlide(
                    id = 1,
                    title = "Local Video Demo",
                    subtitle = "Playing from app assets",
                    imageUrl = "https://example.com/fallback-thumb.jpg",
                    videoAssetPath = "promo_videos/demo.mp4",
                    type = PromoType.LOCAL_VIDEO,
                    backgroundColor = Color(0xFF7C3AED)
                ),
                PromoSlide(
                    id = 2,
                    title = "Image Slide",
                    subtitle = "Regular image content",
                    imageUrl = "https://example.com/image-slide.jpg",
                    type = PromoType.IMAGE,
                    backgroundColor = Color(0xFFEA580C)
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
    videoUrl: String? = null,
    videoAssetPath: String? = null,
    type: PromoType = PromoType.IMAGE,
    backgroundColor: Color = Color(0xFF4F46E5)
): PromoSlide {
    return PromoSlide(
        id = id,
        title = title,
        subtitle = subtitle,
        imageUrl = imageUrl,
        videoUrl = videoUrl,
        videoAssetPath = videoAssetPath,
        type = type,
        backgroundColor = backgroundColor
    )
}