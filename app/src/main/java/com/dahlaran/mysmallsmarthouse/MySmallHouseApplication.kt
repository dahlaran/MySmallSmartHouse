package com.dahlaran.mysmallsmarthouse

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference

class MySmallHouseApplication : Application() {
    companion object {
        var context: WeakReference<Context> = WeakReference(null)
    }

    override fun onCreate() {
        super.onCreate()
        context = WeakReference(this)

    }
}