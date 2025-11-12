package com.aiken.vendingmachine.data.database.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val pid: Int = 0,

    val productName: String,
    val brand: String?,
    val sku: String?,
    val description: String?,
    val productSize: String?, // e.g., "50g", "100g", "150g"
    val sellingPrice: Double,
    val productImages: String?, // local file path (image/video)
    val slotNumber: Int, // 1 to 60, corresponds to vending slot
)
