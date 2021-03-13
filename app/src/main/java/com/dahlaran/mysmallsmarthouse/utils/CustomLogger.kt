package com.dahlaran.mysmallsmarthouse.utils

import android.util.Log
import com.dahlaran.mysmallsmarthouse.BuildConfig

object CustomLogger {

    fun logD(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    fun logE(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }
}