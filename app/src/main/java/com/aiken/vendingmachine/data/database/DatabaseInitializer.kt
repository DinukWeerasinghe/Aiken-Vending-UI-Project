package com.aiken.vendingmachine.data.database

import android.content.Context
import android.util.Log
import com.aiken.vendingmachine.data.database.VendingDatabase
import com.aiken.vendingmachine.utils.ParamConst
import com.aiken.vendingmachine.utils.SharedParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

object DatabaseInitializer {

    private const val TAG = "DatabaseInitializer"
    private const val DB_NAME = "vending_machine.db"

    /**
     * Initializes the database asynchronously (non-blocking).
     * - Triggers creation if DB doesn't exist
     * - Triggers migrations if DB exists
     * - Updates SharedParams (DB_INITIALIZED / DB_LAST_VERSION) on success
     */
    fun initDatabase(context: Context) {
        val dbFile = context.getDatabasePath(DB_NAME)
        val dbExists = dbFile.exists()

        Log.i(TAG, "Database path: ${dbFile.absolutePath}")
        Log.i(TAG, "Database exists: $dbExists")

        // Run the heavy work on IO dispatcher
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Optional: If you want, perform a lightweight backup before migration when DB exists
                // if (dbExists) backupImportantTables(context)

                // Access database to trigger creation/migration
                val db = VendingDatabase.getDatabase(context)

                // Force open writable DB to ensure migrations run now (Room may defer)
                db.openHelper.writableDatabase

                // If we reach here, DB opened successfully
                SharedParams.setBoolean(ParamConst.DB_INITIALIZED, true)
                SharedParams.setInt(ParamConst.DB_LAST_VERSION, VendingDatabase.DB_VERSION)

                Log.i(TAG, "Database initialization/migration succeeded. DB_VERSION=${VendingDatabase.DB_VERSION}")

            } catch (e: Exception) {
                Log.e(TAG, "Database initialization failed", e)

                // Example fallback strategy:
                // 1) If migrations failed and data is critical, try to backup tables, then call rebuildWithDestructiveMigration(context)
                // 2) Otherwise log and escalate
                // NOTE: Rebuild with destructive migration will wipe DB (data loss):
                // VendingDatabase.rebuildWithDestructiveMigration(context)
            }
        }
    }

    /**
     * Example placeholder: backup critical tables to files before destructive migration.
     * Implement export logic as needed (JSON/CSV export) and call before destructive migration.
     */
    private fun backupImportantTables(context: Context) {
        // TODO: implement export logic for important tables (transactions, terminal, etc.)
        Log.i(TAG, "backupImportantTables() - implement export if needed")
    }
}
