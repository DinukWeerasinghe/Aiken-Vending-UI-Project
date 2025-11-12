package com.aiken.vendingmachine.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.core.content.pm.PackageInfoCompat

/**
 * Utility for reading app version and build information.
 *
 * Works for both old and new Android versions using PackageInfoCompat.
 */
object VersionUtils {

    /**
     * Returns the app's versionCode (long to handle modern version codes).
     */
    fun getVersionCode(context: Context): Long {
        return try {
            val pkgInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            PackageInfoCompat.getLongVersionCode(pkgInfo)
        } catch (e: PackageManager.NameNotFoundException) {
            0L
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Returns the app's versionName as defined in build.gradle.
     */
    fun getVersionName(context: Context): String {
        return try {
            val pkgInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pkgInfo.versionName ?: "0.0"
        } catch (e: Exception) {
            "0.0"
        }
    }

    /**
     * Returns the last update time of the app (in milliseconds since epoch).
     */
    fun getLastUpdateTime(context: Context): Long {
        return try {
            val pkgInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pkgInfo.lastUpdateTime
        } catch (e: Exception) {
            0L
        }
    }
}
