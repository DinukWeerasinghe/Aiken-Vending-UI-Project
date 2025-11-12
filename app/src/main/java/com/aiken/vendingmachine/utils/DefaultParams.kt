package com.aiken.vendingmachine.utils

/**
 * Defines system-wide default values for all app parameters.
 * These defaults are applied only when the key is missing in SharedParams.
 */
object DefaultParams {

    // Default values for ParamConst keys
    val defaults = mapOf(
        // 🔹 App info
        ParamConst.APP_VERSION_CODE to "1",
        ParamConst.APP_VERSION_NAME to "1.0",
        ParamConst.APP_LAST_UPDATE_TIME to System.currentTimeMillis().toString(),
        ParamConst.IS_FIRST_LAUNCH to "1",

        // 🔹 Database
        ParamConst.DB_LAST_VERSION to "1",
        ParamConst.DB_INITIALIZED to "0",

        // 🔹 Server / API
        ParamConst.SERVER_URL to "https://api.vending.example.com",
        ParamConst.AUTH_TOKEN to "",

        // 🔹 Vending Machine Defaults
        ParamConst.MACHINE_ID to "0000",
        ParamConst.MACHINE_LOCATION to "Unknown",
        ParamConst.MACHINE_STATUS to "IDLE",
        ParamConst.SLOT_COUNT to "60",
        ParamConst.PRODUCT_LAST_UPDATE to "0",

        // 🔹 UI
        ParamConst.LANGUAGE to "en",
        ParamConst.THEME_MODE to "light",
        ParamConst.SHOW_TUTORIAL to "1",

        // 🔹 Misc
        ParamConst.DEBUG_MODE to "0"
    )

    /**
     * Initializes SharedParams with defaults if not already set.
     */
    fun applyDefaults() {
        for ((key, value) in defaults) {
            if (!SharedParams.contains(key)) {
                SharedParams.setString(key, value)
            }
        }
    }
}
