package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiken.vendingmachine.ui.theme.Error
import com.aiken.vendingmachine.ui.theme.Success
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme

@Composable
fun StockIndicator(
    isInStock: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = CircleShape,
        color = if (isInStock) Success else Error,
        modifier = modifier.size(12.dp)
    ) {}
}

// Preview Functions
@Preview(showBackground = true)
@Composable
private fun StockIndicatorInStockPreview() {
    VendingMachineTheme {
        StockIndicator(
            isInStock = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StockIndicatorOutOfStockPreview() {
    VendingMachineTheme {
        StockIndicator(
            isInStock = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StockIndicatorCustomSizePreview() {
    VendingMachineTheme {
        StockIndicator(
            isInStock = true,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StockIndicatorSmallSizePreview() {
    VendingMachineTheme {
        StockIndicator(
            isInStock = false,
            modifier = Modifier.size(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StockIndicatorBothStatesPreview() {
    VendingMachineTheme {
        // Show both states side by side for comparison
        androidx.compose.foundation.layout.Row {
            StockIndicator(isInStock = true)
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(8.dp))
            StockIndicator(isInStock = false)
        }
    }
}