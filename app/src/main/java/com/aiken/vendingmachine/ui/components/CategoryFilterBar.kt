package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aiken.vendingmachine.data.model.ProductCategory

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
                            //ProductCategory.DRINKS -> "Drinks"
                            //ProductCategory.SNACKS -> "Snacks"
                            //ProductCategory.HEALTHY -> "Healthy"
                            ProductCategory.CREAM_BISCUITS -> TODO()
                            ProductCategory.GLUCOSE -> TODO()
                            ProductCategory.COOKIES -> TODO()
                            ProductCategory.CRACKERS -> TODO()
                            ProductCategory.WAFERS -> TODO()
                            ProductCategory.CHOCOLATE -> TODO()
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = when (category) {
                            ProductCategory.ALL -> Icons.Default.AllInclusive
                            //ProductCategory.DRINKS -> Icons.Default.LocalCafe
                            //ProductCategory.SNACKS -> Icons.Default.LocalDining
                            //ProductCategory.HEALTHY -> Icons.Default.Nature
                            ProductCategory.CREAM_BISCUITS -> TODO()
                            ProductCategory.GLUCOSE -> TODO()
                            ProductCategory.COOKIES -> TODO()
                            ProductCategory.CRACKERS -> TODO()
                            ProductCategory.WAFERS -> TODO()
                            ProductCategory.CHOCOLATE -> TODO()
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