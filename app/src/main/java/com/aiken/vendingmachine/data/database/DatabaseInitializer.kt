package com.aiken.vendingmachine.data.database

import android.content.Context
import android.util.Log

object DatabaseInitializer {

    private const val DB_NAME = "vending_machine.db"

    /**
     * Initializes the database:
     * 1. Checks if the DB file exists
     * 2. Loads migration XML (if available)
     * 3. Creates DB if not found
     * 4. Triggers migrations if needed
     */
    fun initDatabase(context: Context) {
        val dbFile = context.getDatabasePath(DB_NAME)
        val dbExists = dbFile.exists()

        Log.i("DatabaseInitializer", "Database path: ${dbFile.absolutePath}")
        Log.i("DatabaseInitializer", "Database exists: $dbExists")

        try {
            val db = VendingDatabase.getDatabase(context)

            if (!dbExists) {
                Log.i("DatabaseInitializer", "Database not found — creating new one.")
                db.openHelper.writableDatabase // triggers creation
                Log.i("DatabaseInitializer", "Database created successfully.")
            } else {
                Log.i("DatabaseInitializer", "Database found — checking for migrations.")
                db.openHelper.writableDatabase // triggers migration
                Log.i("DatabaseInitializer", "Migration check complete.")
            }

        } catch (e: Exception) {
            Log.e("DatabaseInitializer", "Database initialization failed", e)
        }
    }
}
