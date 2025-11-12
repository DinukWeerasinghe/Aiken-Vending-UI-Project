package com.aiken.vendingmachine.data.database.dao

import androidx.room.*
import com.aiken.vendingmachine.data.database.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // --- Insert ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    // --- Update ---
    @Update
    suspend fun updateProduct(product: Product)

    // --- Delete ---
    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM Product")
    suspend fun deleteAll()

    // --- Query ---
    @Query("SELECT * FROM Product ORDER BY slotNumber ASC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE pid = :id LIMIT 1")
    suspend fun getProductById(id: Int): Product?

    @Query("SELECT * FROM Product WHERE slotNumber = :slot LIMIT 1")
    suspend fun getProductBySlot(slot: Int): Product?

    @Query("SELECT COUNT(*) FROM Product")
    suspend fun getProductCount(): Int
}
