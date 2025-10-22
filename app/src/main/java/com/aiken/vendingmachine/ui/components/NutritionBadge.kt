package com.aiken.vendingmachine.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aiken.vendingmachine.data.model.NutritionInfo
import com.aiken.vendingmachine.ui.theme.Success

@Composable
fun NutritionBadge(
    nutritionInfo: NutritionInfo,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        if (nutritionInfo.isVegan) {
            AssistChip(
                onClick = {},
                label = { Text("🌱 Vegan") },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Success.copy(alpha = 0.1f),
                    labelColor = Success
                ),
                border = null
            )
        }
        if (nutritionInfo.isGlutenFree) {
            AssistChip(
                onClick = {},
                label = { Text("🌾 Gluten-Free") },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    labelColor = MaterialTheme.colorScheme.primary
                ),
                border = null
            )
        }
        if (nutritionInfo.isSugarFree) {
            AssistChip(
                onClick = {},
                label = { Text("🍬 Sugar-Free") },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    labelColor = MaterialTheme.colorScheme.secondary
                ),
                border = null
            )
        }
    }
}