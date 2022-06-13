package com.example.soccernews

import android.app.Application
import com.example.soccernews.App

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @JvmStatic
        var instance: App? = null
            private set
    }
}