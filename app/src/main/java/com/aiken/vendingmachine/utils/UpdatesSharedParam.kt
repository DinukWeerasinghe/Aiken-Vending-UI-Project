package com.aiken.vendingmachine.utils

import android.util.Log

/**
 * Utility to apply default/shared param updates safely on app version upgrade.
 *
 * Behavior:
 * - Keeps any existing SharedParams values as-is.
 * - Adds any missing default params from DefaultParams.defaults.
 * - Runs optional per-version hooks (migrate keys/types) if registered.
 */
object UpdatesSharedParam {

    private const val TAG = "UpdatesSharedParam"

    /**
     * Apply parameter updates on app version upgrade.
     *
     * - oldVersion: previous installed version code (0 if fresh install)
     * - newVersion: current version code
     *
     * This function will:
     *  1. Ensure default params exist (but won't overwrite any keys already present).
     *  2. Run per-version migration hooks (if you add them below).
     */
    fun applyUpdates(oldVersion: Long, newVersion: Long) {
        try {
            Log.i(TAG, "Applying shared-params updates for version change: $oldVersion -> $newVersion")

            // 1) Ensure all default keys are present (but do not overwrite existing ones)
            DefaultParams.defaults.forEach { (key, defaultValue) ->
                if (!SharedParams.contains(key)) {
                    // Use typed setters when possible based on common ParamConst keys:
                    when {
                        isBooleanKey(key) -> {
                            // treat "1" as true, "0" as false for defaults in DefaultParams
                            val boolVal = defaultValue == "1" || defaultValue.equals("true", ignoreCase = true)
                            SharedParams.setBoolean(key, boolVal)
                            Log.i(TAG, "Added missing boolean default for key='$key' value=$boolVal")
                        }
                        isIntKey(key) -> {
                            val intVal = defaultValue.toIntOrNull() ?: 0
                            SharedParams.setInt(key, intVal)
                            Log.i(TAG, "Added missing int default for key='$key' value=$intVal")
                        }
                        isLongKey(key) -> {
                            val longVal = defaultValue.toLongOrNull() ?: 0L
                            SharedParams.setLong(key, longVal)
                            Log.i(TAG, "Added missing long default for key='$key' value=$longVal")
                        }
                        else -> {
                            SharedParams.setString(key, defaultValue)
                            Log.i(TAG, "Added missing string default for key='$key' value='$defaultValue'")
                        }
                    }
                } else {
                    Log.d(TAG, "Key already present; preserving existing value key='$key'")
                }
            }

            // 2) Run version-specific migrations for param keys (if needed).
            // For example, if you renamed a key in v3, migrate old value into new key:
            runPerVersionParamMigrations(oldVersion, newVersion)

            Log.i(TAG, "applyUpdates completed for $oldVersion -> $newVersion")
        } catch (e: Exception) {
            Log.e(TAG, "applyUpdates failed", e)
        }
    }

    // -------------------------
    // Add any per-key typing rules here:
    // -------------------------
    private fun isBooleanKey(key: String): Boolean {
        // Add keys you store as booleans
        return key == ParamConst.IS_FIRST_LAUNCH ||
                key == ParamConst.DB_INITIALIZED ||
                key == ParamConst.SHOW_TUTORIAL ||
                key == ParamConst.DEBUG_MODE
    }

    private fun isIntKey(key: String): Boolean {
        return key == ParamConst.SLOT_COUNT ||
                key == ParamConst.DB_LAST_VERSION
        // add other int keys here
    }

    private fun isLongKey(key: String): Boolean {
        return key == ParamConst.APP_VERSION_CODE ||
                key == ParamConst.APP_LAST_UPDATE_TIME
        // add other long keys here
    }

    // -------------------------
    // Example per-version migrations for param keys
    // -------------------------
    private fun runPerVersionParamMigrations(oldVersion: Long, newVersion: Long) {
        // Example: migrate a renamed key between versions:
        // if (oldVersion < 3 && newVersion >= 3) migrateKey("old_key_name", "new_key_name")
        // Add real migrations as you need them.

        if (oldVersion < 3 && newVersion >= 3) {
            // hypothetical example: KEY_BUTTON_STATE renamed from "button_pressed" -> "button_enabled"
            migrateKeyIfNeeded("button_pressed", "button_enabled", isBoolean = true)
        }

        // Add more migrations following the pattern above
    }

    private fun migrateKeyIfNeeded(oldKey: String, newKey: String, isBoolean: Boolean = false) {
        if (SharedParams.contains(oldKey) && !SharedParams.contains(newKey)) {
            try {
                if (isBoolean) {
                    val value = SharedParams.getBoolean(oldKey, false)
                    SharedParams.setBoolean(newKey, value)
                    Log.i(TAG, "Migrated boolean param $oldKey -> $newKey value=$value")
                } else {
                    val value = SharedParams.getString(oldKey, null)
                    SharedParams.setString(newKey, value)
                    Log.i(TAG, "Migrated string param $oldKey -> $newKey value=$value")
                }
                // optionally remove old key:
                // SharedParams.remove(oldKey)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to migrate param $oldKey -> $newKey", e)
            }
        }
    }
}
