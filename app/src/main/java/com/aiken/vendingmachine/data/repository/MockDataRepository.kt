package com.aiken.vendingmachine.data.repository

import com.aiken.vendingmachine.data.MockData
import com.aiken.vendingmachine.data.model.Product
import com.aiken.vendingmachine.data.model.ProductCategory
import com.aiken.vendingmachine.data.model.PromoSlide
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockDataRepository {
    fun getPromoSlides(): Flow<List<PromoSlide>> = flow {
        emit(MockData.promoSlides)
    }

    fun getAllProducts(): Flow<List<Product>> = flow {
        emit(MockData.products)
    }

    fun getProductsByCategory(category: ProductCategory): Flow<List<Product>> = flow {
        val products = if (category == ProductCategory.ALL) {
            MockData.products
        } else {
            MockData.products.filter { it.category == category }
        }
        emit(products)
    }

    fun getProductById(id: Int): Flow<Product?> = flow {
        val product = MockData.products.find { it.id == id }
        emit(product)
    }
}