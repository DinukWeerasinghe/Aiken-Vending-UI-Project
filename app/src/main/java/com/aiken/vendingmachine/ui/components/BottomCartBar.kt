package com.aiken.vendingmachine.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiken.vendingmachine.ui.theme.Primary
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme

@Composable
fun BottomCartBar(
    itemCount: Int,
    totalPrice: Double,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale = remember { Animatable(1f) }

    // Pulsing animation when items in cart
    LaunchedEffect(itemCount) {
        if (itemCount > 0) {
            scale.animateTo(
                targetValue = 1.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Reverse
                )
            )
        } else {
            scale.animateTo(1f)
        }
    }

    Surface(
        tonalElevation = 8.dp,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Cart icon with badge
            BadgedBox(
                badge = {
                    if (itemCount > 0) {
                        Badge {
                            Text(
                                text = itemCount.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Shopping cart",
                    modifier = Modifier.size(50.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Total price
            Text(
                text = "Total: Rs ${String.format("%.2f", totalPrice)}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Checkout button
            Button(
                onClick = onCheckoutClick,
                enabled = itemCount > 0,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    }
                    .clip(RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                )
            ) {
                Text(
                    text = "Checkout",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

// Preview Functions
@Preview(showBackground = true)
@Composable
private fun BottomCartBarWithItemsPreview() {
    VendingMachineTheme {
        BottomCartBar(
            itemCount = 3,
            totalPrice = 12.50,
            onCheckoutClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomCartBarEmptyPreview() {
    VendingMachineTheme {
        BottomCartBar(
            itemCount = 0,
            totalPrice = 0.0,
            onCheckoutClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomCartBarSingleItemPreview() {
    VendingMachineTheme {
        BottomCartBar(
            itemCount = 1,
            totalPrice = 4.99,
            onCheckoutClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomCartBarManyItemsPreview() {
    VendingMachineTheme {
        BottomCartBar(
            itemCount = 15,
            totalPrice = 47.25,
            onCheckoutClick = { }
        )
    }
}