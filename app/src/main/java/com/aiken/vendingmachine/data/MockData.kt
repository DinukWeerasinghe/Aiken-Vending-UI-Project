package com.aiken.vendingmachine.data

import androidx.compose.ui.graphics.Color
import com.aiken.vendingmachine.data.model.*

object MockData {
    val promoSlides = listOf(
        PromoSlide(
            id = 1,
            title = "Maliban Advertisement",
            subtitle = "Experience the taste of Sri Lanka",
            imageUrl = "https://www.malibangroup.com/images/banners/home-banner-1.jpg",
            backgroundColor = Color(0xFFD32F2F),
            type = PromoType.LOCAL_VIDEO,
            videoAssetPath = "maliban_advertisement.mp4",
        ),
        PromoSlide(
            id = 2,
            title = "New Maliban Chocolate Cream",
            subtitle = "Rich chocolate filling in crispy biscuits",
            imageUrl = "https://www.malibangroup.com/images/products/chocolate-cream.jpg",
            backgroundColor = Color(0xFF7B1FA2),
            type = PromoType.IMAGE
        ),
        PromoSlide(
            id = 3,
            title = "Maliban Glucose - Perfect Energy",
            subtitle = "Instant energy boost for your busy day",
            imageUrl = "https://www.malibangroup.com/images/products/glucose.jpg",
            backgroundColor = Color(0xFF1976D2),
            type = PromoType.LOCAL_VIDEO,
            videoAssetPath = "glucose_promo.mp4" // Another video
        ),
        PromoSlide(
            id = 4,
            title = "Special Offer!",
            subtitle = "Buy 2 Get 1 Free on Selected Items",
            imageUrl = "https://www.malibangroup.com/images/banners/special-offer.jpg",
            backgroundColor = Color(0xFF388E3C),
            type = PromoType.IMAGE
        )
    )
    val products = listOf(
        // Cream Biscuits
        Product(
            id = 1,
            name = "Lemon Puff",
            description = "Light and crispy biscuits with lemon flavored cream filling",
            price = 1.50,
            category = ProductCategory.CREAM_BISCUITS,
            imageUrl = "https://www.malibangroup.com/images/products/lemon-puff.jpg",
            stockLevel = 25,
            shelfPosition = "A1",
            nutritionInfo = NutritionInfo(
                calories = 480,
                ingredients = "Wheat Flour, Sugar, Vegetable Oil, Cream Filling (20%), Lemon Flavor"
            ),
            weight = "200g",
            tags = listOf("cream", "lemon", "sweet")
        ),
        Product(
            id = 2,
            name = "Chocolate Cream",
            description = "Crispy biscuits with rich chocolate cream filling",
            price = 1.75,
            category = ProductCategory.CREAM_BISCUITS,
            imageUrl = "https://www.malibangroup.com/images/products/chocolate-cream.jpg",
            stockLevel = 20,
            shelfPosition = "A2",
            nutritionInfo = NutritionInfo(
                calories = 520,
                ingredients = "Wheat Flour, Sugar, Cocoa, Vegetable Oil, Chocolate Cream"
            ),
            weight = "200g",
            tags = listOf("chocolate", "cream", "sweet")
        ),
        Product(
            id = 3,
            name = "Strawberry Cream",
            description = "Delicious biscuits with strawberry flavored cream",
            price = 1.50,
            category = ProductCategory.CREAM_BISCUITS,
            imageUrl = "https://www.malibangroup.com/images/products/strawberry-cream.jpg",
            stockLevel = 18,
            shelfPosition = "A3",
            nutritionInfo = NutritionInfo(
                calories = 490,
                ingredients = "Wheat Flour, Sugar, Vegetable Oil, Strawberry Cream Filling"
            ),
            weight = "200g",
            tags = listOf("strawberry", "cream", "fruit")
        ),

        // Glucose Biscuits
        Product(
            id = 4,
            name = "Glucose Biscuits",
            description = "Classic glucose biscuits for instant energy",
            price = 1.25,
            category = ProductCategory.GLUCOSE,
            imageUrl = "https://www.malibangroup.com/images/products/glucose.jpg",
            stockLevel = 30,
            shelfPosition = "B1",
            nutritionInfo = NutritionInfo(
                calories = 450,
                ingredients = "Wheat Flour, Glucose, Sugar, Vegetable Oil"
            ),
            weight = "250g",
            tags = listOf("glucose", "energy", "classic")
        ),
        Product(
            id = 5,
            name = "Marie Biscuit",
            description = "Light and crispy tea time biscuits",
            price = 1.20,
            category = ProductCategory.GLUCOSE,
            imageUrl = "https://www.malibangroup.com/images/products/marie.jpg",
            stockLevel = 22,
            shelfPosition = "B2",
            nutritionInfo = NutritionInfo(
                calories = 420,
                ingredients = "Wheat Flour, Sugar, Vegetable Oil, Milk Powder"
            ),
            weight = "250g",
            tags = listOf("marie", "tea-time", "light")
        ),

        // Cookies
        Product(
            id = 6,
            name = "Chocolate Chip Cookies",
            description = "Buttery cookies with real chocolate chips",
            price = 2.00,
            category = ProductCategory.COOKIES,
            imageUrl = "https://www.malibangroup.com/images/products/chocolate-chip.jpg",
            stockLevel = 15,
            shelfPosition = "C1",
            nutritionInfo = NutritionInfo(
                calories = 510,
                ingredients = "Wheat Flour, Chocolate Chips, Butter, Sugar, Eggs"
            ),
            weight = "150g",
            tags = listOf("cookies", "chocolate-chip", "buttery")
        ),
        Product(
            id = 7,
            name = "Oatmeal Cookies",
            description = "Healthy oatmeal cookies with raisins",
            price = 2.25,
            category = ProductCategory.COOKIES,
            imageUrl = "https://www.malibangroup.com/images/products/oatmeal.jpg",
            stockLevel = 12,
            shelfPosition = "C2",
            nutritionInfo = NutritionInfo(
                calories = 430,
                isGlutenFree = true,
                ingredients = "Oats, Raisins, Honey, Vegetable Oil, Cinnamon"
            ),
            weight = "150g",
            tags = listOf("oatmeal", "healthy", "raisins")
        ),

        // Crackers
        Product(
            id = 8,
            name = "Cheese Crackers",
            description = "Savory crackers with cheese flavor",
            price = 1.80,
            category = ProductCategory.CRACKERS,
            imageUrl = "https://www.malibangroup.com/images/products/cheese-crackers.jpg",
            stockLevel = 20,
            shelfPosition = "D1",
            nutritionInfo = NutritionInfo(
                calories = 480,
                ingredients = "Wheat Flour, Cheese Powder, Vegetable Oil, Salt"
            ),
            weight = "180g",
            tags = listOf("cheese", "savory", "crackers")
        ),
        Product(
            id = 9,
            name = "Cream Cracker",
            description = "Classic crispy cream crackers",
            price = 1.40,
            category = ProductCategory.CRACKERS,
            imageUrl = "https://www.malibangroup.com/images/products/cream-cracker.jpg",
            stockLevel = 25,
            shelfPosition = "D2",
            nutritionInfo = NutritionInfo(
                calories = 460,
                ingredients = "Wheat Flour, Vegetable Oil, Salt, Yeast"
            ),
            weight = "200g",
            tags = listOf("cracker", "savory", "crispy")
        ),

        // Wafers
        Product(
            id = 10,
            name = "Chocolate Wafer",
            description = "Crispy wafers with chocolate coating",
            price = 1.90,
            category = ProductCategory.WAFERS,
            imageUrl = "https://www.malibangroup.com/images/products/chocolate-wafer.jpg",
            stockLevel = 18,
            shelfPosition = "E1",
            nutritionInfo = NutritionInfo(
                calories = 530,
                ingredients = "Wheat Flour, Sugar, Cocoa, Vegetable Oil, Milk Powder"
            ),
            weight = "120g",
            tags = listOf("wafer", "chocolate", "crispy")
        ),
        Product(
            id = 11,
            name = "Vanilla Wafer",
            description = "Light wafers with vanilla flavor",
            price = 1.85,
            category = ProductCategory.WAFERS,
            imageUrl = "https://www.malibangroup.com/images/products/vanilla-wafer.jpg",
            stockLevel = 16,
            shelfPosition = "E2",
            nutritionInfo = NutritionInfo(
                calories = 520,
                ingredients = "Wheat Flour, Sugar, Vegetable Oil, Vanilla Flavor"
            ),
            weight = "120g",
            tags = listOf("wafer", "vanilla", "light")
        ),

        // Chocolate Biscuits
        Product(
            id = 12,
            name = "Chocolate Digestive",
            description = "Whole wheat biscuits with chocolate coating",
            price = 2.10,
            category = ProductCategory.CHOCOLATE,
            imageUrl = "https://www.malibangroup.com/images/products/chocolate-digestive.jpg",
            stockLevel = 14,
            shelfPosition = "F1",
            nutritionInfo = NutritionInfo(
                calories = 490,
                ingredients = "Whole Wheat Flour, Chocolate, Sugar, Vegetable Oil"
            ),
            weight = "200g",
            tags = listOf("chocolate", "digestive", "whole-wheat")
        ),
        Product(
            id = 13,
            name = "Choco Milks",
            description = "Milk biscuits with chocolate flavor",
            price = 1.60,
            category = ProductCategory.CHOCOLATE,
            imageUrl = "https://www.malibangroup.com/images/products/choco-milks.jpg",
            stockLevel = 20,
            shelfPosition = "F2",
            nutritionInfo = NutritionInfo(
                calories = 470,
                ingredients = "Wheat Flour, Sugar, Cocoa, Milk Powder, Vegetable Oil"
            ),
            weight = "180g",
            tags = listOf("chocolate", "milk", "biscuit")
        )
    )
}