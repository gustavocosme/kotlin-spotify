package com.gustavo.playerspotify.extentions

import android.content.Context
import android.content.pm.PackageManager

fun Context.isPackageInstalled(packageName: String): Boolean {
    return try {
        this.packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}