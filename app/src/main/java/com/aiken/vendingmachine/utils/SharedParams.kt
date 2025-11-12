package com.aiken.vendingmachine.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * Centralized SharedPreferences manager for Vending Machine app
 *
 * Handles saving, reading, and clearing app-wide parameters
 * (e.g. app version, sync state, user settings, etc.)
 *
 * @author Dinuka
 * @date 2025/11/12
 */
object SharedParams {

    private const val PREF_FILE = "vending_prefs"

    private lateinit var prefs: SharedPreferences

    /**
     * Must be called once (e.g., in Application or MainActivity)
     */
    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences(PREF_FILE, Activity.MODE_PRIVATE)
    }

    private fun ensureInit() {
        if (!::prefs.isInitialized) {
            throw IllegalStateException("SharedParams not initialized. Call SharedParams.init(context) first.")
        }
    }

    // ────────────────────────────────
    // Generic Save / Load
    // ────────────────────────────────

    fun save(params: Map<String, String?>): Boolean {
        ensureInit()
        val editor = prefs.edit()
        params.forEach { (key, value) ->
            editor.putString(key, value)
        }
        return editor.commit()
    }

    fun clear(): Boolean {
        ensureInit()
        return prefs.edit().clear().commit()
    }

    fun remove(key: String): Boolean {
        ensureInit()
        return prefs.edit().remove(key).commit()
    }

    fun getAll(): Map<String, String?> {
        ensureInit()
        return prefs.all.filterValues { it is String }.mapValues { it.value as String? }
    }

    // ────────────────────────────────
    // String
    // ────────────────────────────────

    fun getString(key: String, defaultValue: String? = null): String? {
        ensureInit()
        return prefs.getString(key, defaultValue)
    }

    fun setString(key: String, value: String?): Boolean {
        ensureInit()
        return prefs.edit().putString(key, value).commit()
    }

    // ────────────────────────────────
    // Int
    // ────────────────────────────────

    fun getInt(key: String, defaultValue: Int = 0): Int {
        ensureInit()
        return prefs.getString(key, defaultValue.toString())?.toIntOrNull() ?: defaultValue
    }

    fun setInt(key: String, value: Int): Boolean {
        ensureInit()
        return prefs.edit().putString(key, value.toString()).commit()
    }

    // ────────────────────────────────
    // Long
    // ────────────────────────────────

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        ensureInit()
        return prefs.getString(key, defaultValue.toString())?.toLongOrNull() ?: defaultValue
    }

    fun setLong(key: String, value: Long): Boolean {
        ensureInit()
        return prefs.edit().putString(key, value.toString()).commit()
    }

    // ────────────────────────────────
    // Boolean
    // ────────────────────────────────

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        ensureInit()
        val stored = prefs.getString(key, if (defaultValue) "1" else "0")
        return stored == "1"
    }

    fun setBoolean(key: String, value: Boolean): Boolean {
        ensureInit()
        return prefs.edit().putString(key, if (value) "1" else "0").commit()
    }

    // ────────────────────────────────
    // Utility
    // ────────────────────────────────

    fun contains(key: String): Boolean {
        ensureInit()
        return prefs.contains(key)
    }

    fun logAll() {
        ensureInit()
        Log.i("SharedParams", "All params: ${prefs.all}")
    }
}
