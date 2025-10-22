package com.aiken.vendingmachine.ui.components

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aiken.vendingmachine.data.model.PromoSlide
import com.aiken.vendingmachine.data.model.PromoType
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