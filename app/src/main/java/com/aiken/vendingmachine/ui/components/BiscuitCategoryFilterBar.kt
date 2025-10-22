package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.EnergySavingsLeaf
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aiken.vendingmachine.data.model.ProductCategory
import com.aiken.vendingmachine.ui.theme.*

@Composable
fun BiscuitCategoryFilterBar(
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
                            ProductCategory.CREAM_BISCUITS -> "Cream"
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
                            ProductCategory.GLUCOSE -> Icons.Default.EnergySavingsLeaf
                            ProductCategory.COOKIES -> Icons.Default.Cookie
                            ProductCategory.CRACKERS -> Icons.Default.Grain
                            ProductCategory.WAFERS -> Icons.Default.BreakfastDining
                            ProductCategory.CHOCOLATE -> Icons.Default.Favorite
                        },
                        contentDescription = null,
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = getBiscuitCategoryColor(category),
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}