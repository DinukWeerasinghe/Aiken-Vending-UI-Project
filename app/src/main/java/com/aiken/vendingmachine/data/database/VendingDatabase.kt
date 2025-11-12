package com.aiken.vendingmachine.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aiken.vendingmachine.data.database.dao.ProductDao
import com.aiken.vendingmachine.data.database.entities.Product
import com.aiken.vendingmachine.utils.ParamConst
import com.aiken.vendingmachine.utils.SharedParams

// Keep the annotation version in sync with DB_VERSION below
@androidx.room.Database(
    entities = [
        Product::class
    ],
    version = VendingDatabase.DB_VERSION,
    exportSchema = true
)
abstract class VendingDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        private const val TAG = "VendingDatabase"
        private const val DB_NAME = "vending_machine.db"

        // <-- PUBLIC constant you can reference from other classes
        const val DB_VERSION: Int = 1 // bump to 3 when you change schema

        @Volatile
        private var INSTANCE: VendingDatabase? = null

        fun getDatabase(context: Context): VendingDatabase {
            return INSTANCE ?: synchronized(this) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    VendingDatabase::class.java,
                    DB_NAME
                )

                // Try to load migrations from assets (update_db.xml)
                val migrations = try {
                    DbUpdater.loadUpdateXml(context)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to load migrations from assets", e)
                    null
                }

                if (!migrations.isNullOrEmpty()) {
                    try {
                        builder.addMigrations(*migrations)
                        Log.i(TAG, "Registered ${migrations.size} migration(s) from update_db.xml")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error registering migrations - will use destructive fallback", e)
                        builder.fallbackToDestructiveMigration()
                    }
                } else {
                    Log.w(TAG, "No migrations found in assets. Using fallbackToDestructiveMigration() (data loss possible)")
                    builder.fallbackToDestructiveMigration()
                }

                // Update SharedParams in onCreate/onOpen callbacks
                builder.addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.i(TAG, "Database created (onCreate). Setting DB_INITIALIZED and DB_LAST_VERSION.")
                        try {
                            SharedParams.setBoolean(ParamConst.DB_INITIALIZED, true)
                            SharedParams.setInt(ParamConst.DB_LAST_VERSION, DB_VERSION)
                        } catch (e: Exception) {
                            Log.e(TAG, "Failed to write SharedParams in onCreate", e)
                        }
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.i(TAG, "Database opened (onOpen). Marking DB_LAST_VERSION=$DB_VERSION")
                        try {
                            SharedParams.setBoolean(ParamConst.DB_INITIALIZED, true)
                            SharedParams.setInt(ParamConst.DB_LAST_VERSION, DB_VERSION)
                        } catch (e: Exception) {
                            Log.e(TAG, "Failed to write SharedParams in onOpen", e)
                        }
                    }
                })

                val instance = builder.build()
                INSTANCE = instance
                instance
            }
        }

        fun rebuildWithDestructiveMigration(context: Context) {
            synchronized(this) {
                Log.w(TAG, "Rebuilding database with destructive migration (data loss possible).")
                INSTANCE?.close()
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VendingDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                try {
                    SharedParams.setBoolean(ParamConst.DB_INITIALIZED, true)
                    SharedParams.setInt(ParamConst.DB_LAST_VERSION, DB_VERSION)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to update SharedParams after destructive rebuild", e)
                }
            }
        }
    }
}
