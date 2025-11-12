package com.aiken.vendingmachine.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aiken.vendingmachine.data.database.dao.ProductDao
import com.aiken.vendingmachine.data.database.entities.Product

@Database(
    entities = [
        Product::class
        // Future entities: Terminal, Transactions, Settlements, etc.
    ],
    version = 1,
    exportSchema = true
)
abstract class VendingDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: VendingDatabase? = null

        fun getDatabase(context: Context): VendingDatabase {
            return INSTANCE ?: synchronized(this) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    VendingDatabase::class.java,
                    "vending_machine.db"
                )

                // Load migration XML if available
                val migrations = DbUpdater.loadUpdateXml(context)
                if (!migrations.isNullOrEmpty()) {
                    try {
                        builder.addMigrations(*migrations)
                    } catch (e: Exception) {
                        builder.fallbackToDestructiveMigration()
                    }
                } else {
                    builder.fallbackToDestructiveMigration()
                }

                val instance = builder.build()
                INSTANCE = instance
                instance
            }
        }
    }
}
