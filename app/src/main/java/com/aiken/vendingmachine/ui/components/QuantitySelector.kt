package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    maxQuantity: Int = 10,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            IconButton(
                onClick = { onQuantityChange(quantity - 1) },
                enabled = quantity > 1,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "Decrease quantity"
                )
            }

            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = { onQuantityChange(quantity + 1) },
                enabled = quantity < maxQuantity,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Increase quantity"
                )
            }
        }
    }
}

// Preview Functions
@Preview(showBackground = true)
@Composable
private fun QuantitySelectorMinimumPreview() {
    VendingMachineTheme {
        QuantitySelector(
            quantity = 1,
            onQuantityChange = { },
            maxQuantity = 10
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuantitySelectorMediumPreview() {
    VendingMachineTheme {
        QuantitySelector(
            quantity = 5,
            onQuantityChange = { },
            maxQuantity = 10
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuantitySelectorMaximumPreview() {
    VendingMachineTheme {
        QuantitySelector(
            quantity = 10,
            onQuantityChange = { },
            maxQuantity = 10
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuantitySelectorLowMaxPreview() {
    VendingMachineTheme {
        QuantitySelector(
            quantity = 2,
            onQuantityChange = { },
            maxQuantity = 3
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuantitySelectorSingleItemPreview() {
    VendingMachineTheme {
        QuantitySelector(
            quantity = 1,
            onQuantityChange = { },
            maxQuantity = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuantitySelectorInteractivePreview() {
    VendingMachineTheme {
        var quantity by remember { mutableIntStateOf(1) }

        QuantitySelector(
            quantity = quantity,
            onQuantityChange = { newQuantity ->
                quantity = newQuantity.coerceIn(1, 5)
            },
            maxQuantity = 5
        )
    }
}