package com.example.chowzy

import android.app.Application
import com.example.chowzy.data.source.local.database.AppDatabase

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}