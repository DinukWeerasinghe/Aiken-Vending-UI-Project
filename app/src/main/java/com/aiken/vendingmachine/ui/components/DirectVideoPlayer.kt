package com.aiken.vendingmachine.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun DirectVideoPlayer(
    videoAssetPath: String? = null,
    videoUrl: String? = null,
    autoPlay: Boolean = true,
    loop: Boolean = true,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            // Set up player configuration
            playWhenReady = autoPlay
            repeatMode = if (loop) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Load media when video path changes
    DisposableEffect(videoAssetPath, videoUrl) {
        if (videoAssetPath != null) {
            // Load from assets
            val assetDataSourceFactory = com.google.android.exoplayer2.upstream.DefaultDataSource.Factory(context)
            val mediaItem = MediaItem.fromUri("asset:///$videoAssetPath")
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
        } else if (videoUrl != null) {
            // Load from URL
            val mediaItem = MediaItem.fromUri(videoUrl)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
        }

        onDispose {
            exoPlayer.stop()
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { ctx ->
                StyledPlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false // Hide controls for kiosk mode
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}