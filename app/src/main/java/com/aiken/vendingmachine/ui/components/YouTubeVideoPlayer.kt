package com.aiken.vendingmachine.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.aiken.vendingmachine.data.model.PromoSlide
import com.aiken.vendingmachine.data.model.PromoType

@Composable
fun YouTubeVideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // For YouTube URLs, we'll show a play button that opens the video
        // In a real implementation, you might use YouTube Player API
        Button(
            onClick = {
                openYouTubeVideo(context, videoUrl)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Play Maliban Advertisement",
                style = MaterialTheme.typography.labelLarge
            )
        }

        // Optional: Show thumbnail with play icon overlay
        Text(
            text = "🎬 Tap to play Maliban commercial",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}

private fun openYouTubeVideo(context: Context, videoUrl: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        intent.setPackage("com.google.android.youtube")
        context.startActivity(intent)
    } catch (e: Exception) {
        // Fallback to browser if YouTube app not installed
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        context.startActivity(intent)
    }
}

@Composable
fun EnhancedVideoCarousel(
    promoSlides: List<PromoSlide>,
    currentSlideIndex: Int,
    onSlideChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentSlide = promoSlides.getOrNull(currentSlideIndex)

    Box(modifier = modifier.fillMaxSize()) {
        when (currentSlide?.type) {
            PromoType.VIDEO -> {
                currentSlide.videoUrl?.let { videoUrl ->
                    if (videoUrl.contains("youtube.com") || videoUrl.contains("youtu.be")) {
                        YouTubeVideoPlayer(
                            videoUrl = videoUrl,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        // For direct video URLs (mp4, etc.)
                        DirectVideoPlayer(
                            videoUrl = videoUrl,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } ?: run {
                    // Fallback to image if video URL is null
                    TopCarouselSection(
                        promoSlides = promoSlides,
                        currentSlideIndex = currentSlideIndex,
                        onSlideChange = onSlideChange,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            else -> {
                TopCarouselSection(
                    promoSlides = promoSlides,
                    currentSlideIndex = currentSlideIndex,
                    onSlideChange = onSlideChange,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}