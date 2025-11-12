package com.aiken.vendingmachine.utils

/**
 * Defines all keys used in SharedParams (SharedPreferences)
 * for the Vending Machine project.
 *
 * Keeps all constants centralized and consistent.
 *
 * Example usage:
 * SharedParams.setString(ParamConst.APP_VERSION_NAME, "1.0.0")
 * SharedParams.getBoolean(ParamConst.IS_FIRST_LAUNCH)
 *
 * @author Dinuka
 * @date 2025/11/12
 */
object ParamConst {

    // ────────────────────────────────
    // 🔹 App / Version Info
    // ────────────────────────────────
    const val APP_VERSION_CODE = "app_version_code"
    const val APP_VERSION_NAME = "app_version_name"
    const val APP_LAST_UPDATE_TIME = "app_last_update_time"
    const val IS_FIRST_LAUNCH = "is_first_launch"

    // ────────────────────────────────
    // 🔹 Database / Migration
    // ────────────────────────────────
    const val DB_LAST_VERSION = "db_last_version"
    const val DB_INITIALIZED = "db_initialized"

    // ────────────────────────────────
    // 🔹 Sync & Server Settings
    // ────────────────────────────────
    const val LAST_SYNC_TIME = "last_sync_time"
    const val SERVER_URL = "server_url"
    const val AUTH_TOKEN = "auth_token"

    // ────────────────────────────────
    // 🔹 Vending Machine Info
    // ────────────────────────────────
    const val MACHINE_ID = "machine_id"
    const val MACHINE_LOCATION = "machine_location"
    const val MACHINE_STATUS = "machine_status"
    const val SLOT_COUNT = "slot_count"  // e.g., 60
    const val PRODUCT_LAST_UPDATE = "product_last_update"

    // ────────────────────────────────
    // 🔹 UI / User Preferences
    // ────────────────────────────────
    const val LANGUAGE = "language"
    const val THEME_MODE = "theme_mode"
    const val SHOW_TUTORIAL = "show_tutorial"

    // ────────────────────────────────
    // 🔹 Misc
    // ────────────────────────────────
    const val DEBUG_MODE = "debug_mode"
}
