package com.example.listdummy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplicationCustom: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}