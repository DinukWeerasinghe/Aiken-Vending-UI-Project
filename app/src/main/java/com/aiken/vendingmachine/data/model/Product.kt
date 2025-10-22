package com.aiken.vendingmachine.data.model

import androidx.compose.ui.graphics.Color

enum class ProductCategory {
    CREAM_BISCUITS, GLUCOSE, COOKIES, CRACKERS, WAFERS, CHOCOLATE, ALL
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
    val tags: List<String> = emptyList(),
    val brand: String = "Maliban",
    val weight: String = "" // e.g., "200g", "100g"
)

data class NutritionInfo(
    val calories: Int,
    val isVegan: Boolean = false,
    val isGlutenFree: Boolean = false,
    val isSugarFree: Boolean = false,
    val ingredients: String = ""
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
    val backgroundColor: Color,
    val type: PromoType = PromoType.IMAGE,
    val videoUrl: String? = null,
    val videoAssetPath: String? = null // 👈 Add this line
)


enum class PromoType {
    IMAGE, VIDEO,LOCAL_VIDEO
}

enum class PaymentMethod {
    CASH, CARD, DIGITAL_WALLET, QR_CODE
}