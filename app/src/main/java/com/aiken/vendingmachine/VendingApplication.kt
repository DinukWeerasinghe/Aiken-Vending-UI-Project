package com.aiken.vendingmachine

import android.app.Application
import android.util.Log
import com.aiken.vendingmachine.data.database.VendingDatabase
import com.aiken.vendingmachine.utils.DefaultParams
import com.aiken.vendingmachine.utils.ParamConst
import com.aiken.vendingmachine.utils.SharedParams
import com.aiken.vendingmachine.utils.UpdatesSharedParam
import com.aiken.vendingmachine.utils.VersionUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VendingApplication : Application() {

    companion object {
        private const val TAG = "VendingApplicationLogs"
    }

    override fun onCreate() {
        super.onCreate()
        SharedParams.init(this)

        //Apply defaults if missing
        DefaultParams.applyDefaults()

        // Continue startup (version + DB check)
        checkForVersionOrDbUpdate()
    }


    private fun checkForVersionOrDbUpdate() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // App version check
                val currentCode = VersionUtils.getVersionCode(this@VendingApplication)
                val storedCode = SharedParams.getLong(ParamConst.APP_VERSION_CODE, 0L)

                if (currentCode != storedCode) {
                    Log.i(TAG, "App updated: old=$storedCode new=$currentCode")
                    handleAppUpgrade(storedCode, currentCode)
                } else {
                    Log.i(TAG, "App version unchanged: $currentCode")
                }

                // DB version check — use the public DB_VERSION constant in VendingDatabase
                val currentDbVersion = VendingDatabase.DB_VERSION
                val storedDbVersion = SharedParams.getInt(ParamConst.DB_LAST_VERSION, 1)

                if (storedDbVersion != currentDbVersion) {
                    Log.i(TAG, "DB schema changed (or first run): old=$storedDbVersion new=$currentDbVersion")

                    try {
                        // Trigger Room to open and apply migrations (if any)
                        val db = VendingDatabase.getDatabase(this@VendingApplication)
                        db.openHelper.writableDatabase

                        // If successful, update SharedParams
                        SharedParams.setInt(ParamConst.DB_LAST_VERSION, currentDbVersion)
                        SharedParams.setBoolean(ParamConst.DB_INITIALIZED, true)
                        Log.i(TAG, "DB open/migration succeeded. DB_LAST_VERSION set to $currentDbVersion")
                    } catch (dbEx: Exception) {
                        Log.e(TAG, "Opening database failed during migration", dbEx)
                        // TODO: decide fallback: backup + rebuildWithDestructiveMigration(context)
                    }
                } else {
                    Log.i(TAG, "DB version unchanged: $storedDbVersion")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Version/DB check failed", e)
            }
        }
    }

    private fun handleAppUpgrade(oldVersion: Long, newVersion: Long) {
        try {
            // 1) Apply shared-params updates while preserving existing values
            UpdatesSharedParam.applyUpdates(oldVersion, newVersion)

            // 2) Store latest app info
            SharedParams.setLong(ParamConst.APP_VERSION_CODE, newVersion)
            SharedParams.setString(ParamConst.APP_VERSION_NAME, VersionUtils.getVersionName(this))
            SharedParams.setLong(ParamConst.APP_LAST_UPDATE_TIME, VersionUtils.getLastUpdateTime(this))

            // First install or upgrade logic
            if (oldVersion == 0L) {
                SharedParams.setBoolean(ParamConst.IS_FIRST_LAUNCH, true)
                Log.i("VendingApp", "First launch detected.")
            } else {
                SharedParams.setBoolean(ParamConst.IS_FIRST_LAUNCH, false)
                Log.i("VendingApp", "App upgraded from $oldVersion to $newVersion")
                // optional: clear caches, sync server, or show changelog
            }
        } catch (e: Exception) {
            Log.e("VendingApp", "Failed to handle app upgrade", e)
        }
    }
}
