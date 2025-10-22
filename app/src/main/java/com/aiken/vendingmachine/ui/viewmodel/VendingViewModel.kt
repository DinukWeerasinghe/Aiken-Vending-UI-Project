package com.aiken.vendingmachine.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiken.vendingmachine.data.model.CartItem
import com.aiken.vendingmachine.data.model.PaymentMethod
import com.aiken.vendingmachine.data.model.Product
import com.aiken.vendingmachine.data.model.ProductCategory
import com.aiken.vendingmachine.data.model.PromoSlide
import com.aiken.vendingmachine.data.repository.MockDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class VendingViewModel : ViewModel() {
    private val repository = MockDataRepository()

    private val _uiState = MutableStateFlow(VendingUiState())
    val uiState: StateFlow<VendingUiState> = _uiState.asStateFlow()

    private val _products = MutableStateFlow(emptyList<Product>())
    private val _promoSlides = MutableStateFlow(emptyList<PromoSlide>())

    var selectedCategory by mutableStateOf(ProductCategory.ALL)
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getAllProducts().collect { products ->
                _products.value = products
                updateFilteredProducts()
            }
        }

        viewModelScope.launch {
            repository.getPromoSlides().collect { slides ->
                _promoSlides.value = slides
                _uiState.value = _uiState.value.copy(promoSlides = slides)
            }
        }
    }

    fun addToCart(product: Product) {
        val currentCart = _uiState.value.cartItems.toMutableList()
        val existingItem = currentCart.find { it.product.id == product.id }

        if (existingItem != null) {
            // Update quantity
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            currentCart[currentCart.indexOf(existingItem)] = updatedItem
        } else {
            // Add new item
            currentCart.add(CartItem(product, 1))
        }

        updateCart(currentCart)
    }

    fun removeFromCart(productId: Int) {
        val currentCart = _uiState.value.cartItems.toMutableList()
        currentCart.removeAll { it.product.id == productId }
        updateCart(currentCart)
    }

    fun updateQuantity(productId: Int, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(productId)
            return
        }

        val currentCart = _uiState.value.cartItems.toMutableList()
        val existingItem = currentCart.find { it.product.id == productId }

        existingItem?.let {
            val updatedItem = it.copy(quantity = newQuantity)
            currentCart[currentCart.indexOf(it)] = updatedItem
            updateCart(currentCart)
        }
    }

    private fun updateCart(cartItems: List<CartItem>) {
        val totalPrice = cartItems.sumOf { it.product.price * it.quantity }
        val cartItemCount = cartItems.sumOf { it.quantity }

        _uiState.value = _uiState.value.copy(
            cartItems = cartItems,
            totalPrice = totalPrice,
            cartItemCount = cartItemCount
        )
    }

    fun filterByCategory(category: ProductCategory) {
        selectedCategory = category
        updateFilteredProducts()
    }

    private fun updateFilteredProducts() {
        val filteredProducts = if (selectedCategory == ProductCategory.ALL) {
            _products.value
        } else {
            _products.value.filter { it.category == selectedCategory }
        }

        _uiState.value = _uiState.value.copy(
            products = filteredProducts,
            selectedCategory = selectedCategory
        )
    }

    fun selectProduct(product: Product) {
        _uiState.value = _uiState.value.copy(selectedProduct = product)
    }

    fun clearSelectedProduct() {
        _uiState.value = _uiState.value.copy(selectedProduct = null)
    }

    fun clearCart() {
        _uiState.value = _uiState.value.copy(
            cartItems = emptyList(),
            totalPrice = 0.0,
            cartItemCount = 0
        )
    }

    fun selectPaymentMethod(method: PaymentMethod) {
        _uiState.value = _uiState.value.copy(selectedPaymentMethod = method)
    }

    fun processPayment(method: PaymentMethod) {
        _uiState.value = _uiState.value.copy(
            selectedPaymentMethod = method,
            isProcessingPayment = true
        )

        // Simulate payment processing
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000) // 3 seconds processing
            _uiState.value = _uiState.value.copy(
                isProcessingPayment = false,
                showSuccessScreen = true
            )
        }
    }

    fun resetToIdle() {
        _uiState.value = VendingUiState(
            promoSlides = _uiState.value.promoSlides,
            products = _uiState.value.products
        )
        selectedCategory = ProductCategory.ALL
        updateFilteredProducts()
    }

    fun completePurchase() {
        clearCart()
        _uiState.value = _uiState.value.copy(
            showSuccessScreen = false,
            selectedPaymentMethod = null
        )
    }

    fun updateCurrentSlide(index: Int) {
        _uiState.value = _uiState.value.copy(currentSlideIndex = index)
    }
}

data class VendingUiState(
    val products: List<Product> = emptyList(),
    val cartItems: List<CartItem> = emptyList(),
    val selectedCategory: ProductCategory = ProductCategory.ALL,
    val currentSlideIndex: Int = 0,
    val promoSlides: List<PromoSlide> = emptyList(),
    val totalPrice: Double = 0.0,
    val cartItemCount: Int = 0,
    val isLoading: Boolean = false,
    val selectedProduct: Product? = null,
    val selectedPaymentMethod: PaymentMethod? = null,
    val isProcessingPayment: Boolean = false,
    val showSuccessScreen: Boolean = false,
    val errorMessage: String? = null
)