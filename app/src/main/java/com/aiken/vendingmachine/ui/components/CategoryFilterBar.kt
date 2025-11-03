package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiken.vendingmachine.data.model.ProductCategory
import com.aiken.vendingmachine.ui.theme.VendingMachineTheme

@Composable
fun CategoryFilterBar(
    selectedCategory: ProductCategory,
    onCategorySelected: (ProductCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        ProductCategory.entries.forEach { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        text = when (category) {
                            ProductCategory.ALL -> "All"
                            ProductCategory.CREAM_BISCUITS -> "Cream Biscuits"
                            ProductCategory.GLUCOSE -> "Glucose"
                            ProductCategory.COOKIES -> "Cookies"
                            ProductCategory.CRACKERS -> "Crackers"
                            ProductCategory.WAFERS -> "Wafers"
                            ProductCategory.CHOCOLATE -> "Chocolate"
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = when (category) {
                            ProductCategory.ALL -> Icons.Default.AllInclusive
                            ProductCategory.CREAM_BISCUITS -> Icons.Default.Cake
                            ProductCategory.GLUCOSE -> Icons.Default.Spa
                            ProductCategory.COOKIES -> Icons.Default.Cookie
                            ProductCategory.CRACKERS -> Icons.Default.Grain
                            ProductCategory.WAFERS -> Icons.Default.BakeryDining
                            ProductCategory.CHOCOLATE -> Icons.Default.BreakfastDining
                        },
                        contentDescription = null,
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

// Preview Functions
@Preview(showBackground = true)
@Composable
private fun CategoryFilterBarAllSelectedPreview() {
    VendingMachineTheme {
        CategoryFilterBar(
            selectedCategory = ProductCategory.ALL,
            onCategorySelected = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryFilterBarCreamBiscuitsSelectedPreview() {
    VendingMachineTheme {
        CategoryFilterBar(
            selectedCategory = ProductCategory.CREAM_BISCUITS,
            onCategorySelected = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryFilterBarCookiesSelectedPreview() {
    VendingMachineTheme {
        CategoryFilterBar(
            selectedCategory = ProductCategory.COOKIES,
            onCategorySelected = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryFilterBarChocolateSelectedPreview() {
    VendingMachineTheme {
        CategoryFilterBar(
            selectedCategory = ProductCategory.CHOCOLATE,
            onCategorySelected = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryFilterBarGlucoseSelectedPreview() {
    VendingMachineTheme {
        CategoryFilterBar(
            selectedCategory = ProductCategory.GLUCOSE,
            onCategorySelected = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryFilterBarCrackersSelectedPreview() {
    VendingMachineTheme {
        CategoryFilterBar(
            selectedCategory = ProductCategory.CRACKERS,
            onCategorySelected = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryFilterBarWafersSelectedPreview() {
    VendingMachineTheme {
        CategoryFilterBar(
            selectedCategory = ProductCategory.WAFERS,
            onCategorySelected = { }
        )
    }
}