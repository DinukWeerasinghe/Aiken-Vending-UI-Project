package com.aiken.vendingmachine.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aiken.vendingmachine.R
import com.aiken.vendingmachine.ui.theme.Primary
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme
import com.aiken.vendingmachine.ui.viewmodel.VendingViewModel

@Composable
fun MalibanIdleScreen(
    onTouchToStart: () -> Unit,
    viewModel: VendingViewModel = viewModel()
) {
    val pulse = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        pulse.animateTo(
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Surface(
        onClick = onTouchToStart,
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            // Maliban Logo
            Image(
                painter = painterResource(id = R.drawable.maliban_logo), // You'll need to add Maliban logo
                contentDescription = "Maliban Biscuits",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Maliban Biscuits",
                style = MaterialTheme.typography.displayLarge,
                color = Primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Taste the Difference Since 1954",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Touch screen to start shopping",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = pulse.value
                        scaleY = pulse.value
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "👆",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = pulse.value
                        scaleY = pulse.value
                    }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Sri Lanka's Favorite Biscuit Manufacturer",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

// Preview Functions
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MalibanIdleScreenPreview() {
    VendingMachineTheme {
        MalibanIdleScreen(
            onTouchToStart = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, widthDp = 1080, heightDp = 1920)
@Composable
private fun MalibanIdleScreenTabletPreview() {
    VendingMachineTheme {
        MalibanIdleScreen(
            onTouchToStart = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, widthDp = 411, heightDp = 891)
@Composable
private fun MalibanIdleScreenMobilePreview() {
    VendingMachineTheme {
        MalibanIdleScreen(
            onTouchToStart = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, widthDp = 600, heightDp = 1024)
@Composable
private fun MalibanIdleScreenLargePreview() {
    VendingMachineTheme {
        MalibanIdleScreen(
            onTouchToStart = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MalibanIdleScreenAnimationPreview() {
    VendingMachineTheme {
        // This preview will show the pulsing animation
        MalibanIdleScreen(
            onTouchToStart = { }
        )
    }
}

// Alternative preview without the logo (in case you don't have the drawable yet)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MalibanIdleScreenNoLogoPreview() {
    VendingMachineTheme {
        Surface(
            onClick = { },
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                // Placeholder for logo
                Surface(
                    color = Primary.copy(alpha = 0.1f),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.size(200.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "MALIBAN",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "LOGO",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Primary.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Maliban Biscuits",
                    style = MaterialTheme.typography.displayLarge,
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Taste the Difference Since 1954",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Touch screen to start shopping",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "👆",
                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Sri Lanka's Favorite Biscuit Manufacturer",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}