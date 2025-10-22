package com.aiken.vendingmachine.data

import androidx.compose.ui.graphics.Color
import com.aiken.vendingmachine.data.model.*

object MockData {
    val promoSlides = listOf(
        PromoSlide(
            id = 1,
            title = "New Energy Drinks!",
            subtitle = "Try our latest energy boosters",
            imageUrl = "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=800",
            backgroundColor = Color(0xFF7C3AED)
        ),
        PromoSlide(
            id = 2,
            title = "Healthy Snacks 20% Off",
            subtitle = "Fresh and nutritious options",
            imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=800",
            backgroundColor = Color(0xFF10B981)
        ),
        PromoSlide(
            id = 3,
            title = "Fresh Juice Available",
            subtitle = "100% natural fruit juices",
            imageUrl = "https://images.unsplash.com/photo-1613478223719-2ab802602423?w=800",
            backgroundColor = Color(0xFFF97316)
        )
    )

    val products = listOf(
        // Drinks
        Product(
            id = 1,
            name = "Coca Cola",
            description = "Classic cola drink, 330ml",
            price = 2.50,
            category = ProductCategory.DRINKS,
            imageUrl = "https://images.unsplash.com/photo-1554866585-cd94860890b7?w=400",
            stockLevel = 15,
            shelfPosition = "A1",
            nutritionInfo = NutritionInfo(139, false, true, false),
            tags = listOf("carbonated")
        ),
        Product(
            id = 2,
            name = "Pepsi",
            description = "Refreshing cola beverage, 330ml",
            price = 2.50,
            category = ProductCategory.DRINKS,
            imageUrl = "https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=400",
            stockLevel = 12,
            shelfPosition = "A2",
            nutritionInfo = NutritionInfo(150, false, true, false),
            tags = listOf("carbonated")
        ),
        Product(
            id = 3,
            name = "Sprite",
            description = "Lemon-lime soda, 330ml",
            price = 2.50,
            category = ProductCategory.DRINKS,
            imageUrl = "https://images.unsplash.com/photo-1631549916768-4119b2e5f926?w=400",
            stockLevel = 8,
            shelfPosition = "A3",
            nutritionInfo = NutritionInfo(146, false, true, false),
            tags = listOf("carbonated", "lemon-lime")
        ),
        Product(
            id = 4,
            name = "Fanta Orange",
            description = "Orange flavored soda, 330ml",
            price = 2.50,
            category = ProductCategory.DRINKS,
            imageUrl = "https://images.unsplash.com/photo-1624517452488-04869289fb43?w=400",
            stockLevel = 10,
            shelfPosition = "A4",
            nutritionInfo = NutritionInfo(160, false, true, false),
            tags = listOf("carbonated", "orange")
        ),
        Product(
            id = 5,
            name = "Monster Energy",
            description = "Energy drink, 500ml",
            price = 3.75,
            category = ProductCategory.DRINKS,
            imageUrl = "https://images.unsplash.com/photo-1619002351733-95e69ba49c2e?w=400",
            stockLevel = 6,
            shelfPosition = "A5",
            nutritionInfo = NutritionInfo(110, false, true, false),
            tags = listOf("energy", "caffeine")
        ),
        Product(
            id = 6,
            name = "Red Bull",
            description = "Energy drink, 250ml",
            price = 3.75,
            category = ProductCategory.DRINKS,
            imageUrl = "https://images.unsplash.com/photo-1603398938373-e54da0bb5e48?w=400",
            stockLevel = 0,
            shelfPosition = "A6",
            nutritionInfo = NutritionInfo(112, false, true, false),
            tags = listOf("energy", "caffeine")
        ),
        Product(
            id = 7,
            name = "Mineral Water",
            description = "Pure spring water, 500ml",
            price = 1.50,
            category = ProductCategory.DRINKS,
            imageUrl = "https://images.unsplash.com/photo-1548839140-29a749e1cf4d?w=400",
            stockLevel = 20,
            shelfPosition = "B1",
            nutritionInfo = NutritionInfo(0, true, true, true),
            tags = listOf("water", "hydration")
        ),
        Product(
            id = 8,
            name = "Orange Juice",
            description = "100% fresh orange juice, 300ml",
            price = 3.00,
            category = ProductCategory.DRINKS,
            imageUrl = "https://images.unsplash.com/photo-1613478223719-2ab802602423?w=400",
            stockLevel = 8,
            shelfPosition = "B2",
            nutritionInfo = NutritionInfo(112, true, true, false),
            tags = listOf("juice", "fruit", "vitamin-c")
        ),
        Product(
            id = 9,
            name = "Apple Juice",
            description = "100% apple juice, 300ml",
            price = 3.00,
            category = ProductCategory.DRINKS,
            imageUrl = "https://images.unsplash.com/photo-1572816703439-d6b3e5495cb7?w=400",
            stockLevel = 7,
            shelfPosition = "B3",
            nutritionInfo = NutritionInfo(117, true, true, false),
            tags = listOf("juice", "fruit")
        ),

        // Snacks
        Product(
            id = 10,
            name = "Lays Classic",
            description = "Classic potato chips, 50g",
            price = 1.99,
            category = ProductCategory.SNACKS,
            imageUrl = "https://images.unsplash.com/photo-1566474982192-8d2738f45849?w=400",
            stockLevel = 15,
            shelfPosition = "C1",
            nutritionInfo = NutritionInfo(160, true, true, false),
            tags = listOf("chips", "potato")
        ),
        Product(
            id = 11,
            name = "Doritos Nacho",
            description = "Nacho cheese flavored chips, 50g",
            price = 2.25,
            category = ProductCategory.SNACKS,
            imageUrl = "https://images.unsplash.com/photo-1558961360-7717c5bddfaf?w=400",
            stockLevel = 12,
            shelfPosition = "C2",
            nutritionInfo = NutritionInfo(140, true, true, false),
            tags = listOf("chips", "cheese")
        ),
        Product(
            id = 12,
            name = "Cheetos Crunchy",
            description = "Crunchy cheese snacks, 45g",
            price = 2.25,
            category = ProductCategory.SNACKS,
            imageUrl = "https://images.unsplash.com/photo-1566474982192-8d2738f45849?w=400",
            stockLevel = 10,
            shelfPosition = "C3",
            nutritionInfo = NutritionInfo(150, true, true, false),
            tags = listOf("cheese", "crunchy")
        ),
        Product(
            id = 13,
            name = "Pringles Original",
            description = "Original potato crisps, 100g",
            price = 2.75,
            category = ProductCategory.SNACKS,
            imageUrl = "https://images.unsplash.com/photo-1599490659213-e2b9527bd087?w=400",
            stockLevel = 8,
            shelfPosition = "C4",
            nutritionInfo = NutritionInfo(150, true, true, false),
            tags = listOf("chips", "stackable")
        ),
        Product(
            id = 14,
            name = "Snickers",
            description = "Chocolate bar with peanuts, 50g",
            price = 1.50,
            category = ProductCategory.SNACKS,
            imageUrl = "https://images.unsplash.com/photo-1575370333363-efc8b47eac2a?w=400",
            stockLevel = 20,
            shelfPosition = "D1",
            nutritionInfo = NutritionInfo(250, false, true, false),
            tags = listOf("chocolate", "peanuts", "caramel")
        ),
        Product(
            id = 15,
            name = "Kit Kat",
            description = "Chocolate wafer bar, 45g",
            price = 1.50,
            category = ProductCategory.SNACKS,
            imageUrl = "https://images.unsplash.com/photo-1623334044303-241021148842?w=400",
            stockLevel = 18,
            shelfPosition = "D2",
            nutritionInfo = NutritionInfo(209, false, true, false),
            tags = listOf("chocolate", "wafer")
        ),
        Product(
            id = 16,
            name = "M&Ms",
            description = "Colorful chocolate candies, 45g",
            price = 1.50,
            category = ProductCategory.SNACKS,
            imageUrl = "https://images.unsplash.com/photo-1570824104450-971c53d1c936?w=400",
            stockLevel = 15,
            shelfPosition = "D3",
            nutritionInfo = NutritionInfo(240, false, true, false),
            tags = listOf("chocolate", "colorful")
        ),

        // Healthy
        Product(
            id = 17,
            name = "Protein Bar",
            description = "High protein nutrition bar, 60g",
            price = 2.50,
            category = ProductCategory.HEALTHY,
            imageUrl = "https://images.unsplash.com/photo-1517433670267-08e4a6e5e0f3?w=400",
            stockLevel = 12,
            shelfPosition = "E1",
            nutritionInfo = NutritionInfo(200, true, true, true),
            tags = listOf("protein", "healthy", "energy")
        ),
        Product(
            id = 18,
            name = "Granola Bar",
            description = "Oats and honey granola bar, 35g",
            price = 2.50,
            category = ProductCategory.HEALTHY,
            imageUrl = "https://images.unsplash.com/photo-1488477181946-6428a0291777?w=400",
            stockLevel = 10,
            shelfPosition = "E2",
            nutritionInfo = NutritionInfo(140, true, true, false),
            tags = listOf("granola", "oats", "healthy")
        ),
        Product(
            id = 19,
            name = "Trail Mix",
            description = "Mixed nuts and dried fruits, 100g",
            price = 3.50,
            category = ProductCategory.HEALTHY,
            imageUrl = "https://images.unsplash.com/photo-1505253716362-afaea1d3d1af?w=400",
            stockLevel = 8,
            shelfPosition = "E3",
            nutritionInfo = NutritionInfo(460, true, true, true),
            tags = listOf("nuts", "fruits", "healthy")
        ),
        Product(
            id = 20,
            name = "Mixed Nuts",
            description = "Assorted roasted nuts, 75g",
            price = 3.50,
            category = ProductCategory.HEALTHY,
            imageUrl = "https://images.unsplash.com/photo-1533089860892-a7c6f0a88666?w=400",
            stockLevel = 9,
            shelfPosition = "E4",
            nutritionInfo = NutritionInfo(450, true, true, true),
            tags = listOf("nuts", "protein", "healthy")
        )
    )
}