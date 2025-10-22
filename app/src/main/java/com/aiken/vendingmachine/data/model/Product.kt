package com.aiken.vendingmachine.data.model

import androidx.compose.ui.graphics.Color

enum class ProductCategory {
    DRINKS, SNACKS, HEALTHY, ALL
}

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: ProductCategory,
    val imageUrl: String,
    val stockLevel: Int,
    val shelfPosition: String,
    val nutritionInfo: NutritionInfo,
    val isAvailable: Boolean = true,
    val tags: List<String> = emptyList()
)

data class NutritionInfo(
    val calories: Int,
    val isVegan: Boolean = false,
    val isGlutenFree: Boolean = false,
    val isSugarFree: Boolean = false
)

data class CartItem(
    val product: Product,
    val quantity: Int
)

data class PromoSlide(
    val id: Int,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val backgroundColor: Color
)

enum class PaymentMethod {
    CASH, CARD, DIGITAL_WALLET, QR_CODE
}