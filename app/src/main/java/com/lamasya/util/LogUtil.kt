package com.lamasya.util

import android.content.Context
import android.util.Log

fun Context.logE(message: String) {
    Log.e(this.toString(), message)
}