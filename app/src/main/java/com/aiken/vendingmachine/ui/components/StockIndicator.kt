package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aiken.vendingmachine.ui.theme.Error
import com.aiken.vendingmachine.ui.theme.Success

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